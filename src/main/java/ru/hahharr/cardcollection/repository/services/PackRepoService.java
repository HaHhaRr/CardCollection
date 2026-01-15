package ru.hahharr.cardcollection.repository.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import ru.hahharr.cardcollection.models.dto.AddPackRequestDto;
import ru.hahharr.cardcollection.models.dto.CardListResponseDto;
import ru.hahharr.cardcollection.models.dto.PackViewResponseDto;
import ru.hahharr.cardcollection.models.entity.Pack;
import ru.hahharr.cardcollection.models.orm.CardOrm;
import ru.hahharr.cardcollection.models.orm.CollectionOrm;
import ru.hahharr.cardcollection.models.orm.DropChanceOrm;
import ru.hahharr.cardcollection.models.orm.PackOrm;
import ru.hahharr.cardcollection.models.primitives.id.CardId;
import ru.hahharr.cardcollection.models.primitives.id.CollectionId;
import ru.hahharr.cardcollection.models.primitives.id.PackId;
import ru.hahharr.cardcollection.repository.interfaces.CardRepository;
import ru.hahharr.cardcollection.repository.interfaces.CollectionRepository;
import ru.hahharr.cardcollection.repository.interfaces.PackRepository;
import ru.hahharr.cardcollection.utils.OffsetLimitPage;
import ru.hahharr.cardcollection.utils.mapper.EntityFromOrmMapper;
import ru.hahharr.cardcollection.utils.mapper.EntityToViewMapper;
import ru.hahharr.cardcollection.utils.mapper.ViewFromOrmMapper;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
public class PackRepoService {

    @Autowired
    private PackRepository packRepository;

    @Autowired
    private CollectionRepository collectionRepository;

    @Autowired
    private CardRepository cardRepository;

    public ResponseEntity<HttpStatus> saveNewPack(AddPackRequestDto addPackRequestDto) {

        if (isValidParam(addPackRequestDto.getCollectionId(),
                addPackRequestDto.getCost(),
                addPackRequestDto.getListIds())) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        CollectionOrm collectionOrm = collectionRepository.findById(addPackRequestDto.getCollectionId()).get();

        PackOrm newPackOrm = new PackOrm(addPackRequestDto.getPackName(),
                addPackRequestDto.getCost(),
                addPackRequestDto.getListIds(),
                collectionOrm);

        newPackOrm.setDropChanceOrm(DropChanceOrm.createFromChances(
                addPackRequestDto.getCommonDropChance(),
                addPackRequestDto.getRareDropChance(),
                addPackRequestDto.getEpicDropChance(),
                newPackOrm));
        packRepository.save(newPackOrm);

        return new ResponseEntity<>(HttpStatus.OK);
    }

    public ResponseEntity<PackViewResponseDto> getAllPacks(int offset, int limit) {
        Page<PackOrm> packOrmPage = packRepository.findAll(OffsetLimitPage.of(offset, limit));
        PackViewResponseDto packViewResponseDto = new PackViewResponseDto(
                packOrmPage
                        .map(ViewFromOrmMapper::mapPack)
                        .stream()
                        .toList(), packOrmPage.getTotalPages());
        return new ResponseEntity<>(packViewResponseDto, HttpStatus.OK);
    }

    public ResponseEntity<CardListResponseDto> getCardsFromPack(PackId packId, int offset, int limit) {
        Optional<PackOrm> packOrm = getPack(packId);
        if (packOrm.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        Page<CardOrm> cardListPage = cardRepository.findByIdIn(packOrm.get().getCards(),
                OffsetLimitPage.of(offset, limit));
        CardListResponseDto cardListResponseDto = new CardListResponseDto(
                cardListPage.stream()
                        .map(EntityFromOrmMapper::mapCard)
                        .toList(), cardListPage.getTotalPages());

        return new ResponseEntity<>(cardListResponseDto, HttpStatus.OK);
    }

    private Optional<PackOrm> getPack(PackId packId) {
        return packRepository.findById(packId);
    }

    private boolean isValidParam(CollectionId collectionId, int cost, List<CardId> listIds) {
        Set<CardId> setIds = new HashSet<>(listIds);
        return !checkSameId(collectionId, listIds)
                || setIds.size() != listIds.size()
                || cost < 0;
    }

    private boolean checkSameId(CollectionId collectionId, List<CardId> listIds) {
        Optional<CollectionOrm> collectionOrm = collectionRepository.findById(collectionId);
        return collectionOrm.filter(orm -> cardRepository.findCardIdsByCollectionOrm(orm)
                .containsAll(listIds)).isPresent();
    }
}
