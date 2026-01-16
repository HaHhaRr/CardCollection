package ru.hahharr.cardcollection.repository.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import ru.hahharr.cardcollection.models.dto.CardListResponseDto;
import ru.hahharr.cardcollection.models.dto.CollectionViewResponseDto;
import ru.hahharr.cardcollection.models.dto.PackViewResponseDto;
import ru.hahharr.cardcollection.models.orm.CardOrm;
import ru.hahharr.cardcollection.models.orm.CollectionOrm;
import ru.hahharr.cardcollection.models.orm.PackOrm;
import ru.hahharr.cardcollection.models.primitives.id.CardId;
import ru.hahharr.cardcollection.models.primitives.id.CollectionId;
import ru.hahharr.cardcollection.models.primitives.id.PackId;
import ru.hahharr.cardcollection.repository.interfaces.CardRepository;
import ru.hahharr.cardcollection.repository.interfaces.CollectionRepository;
import ru.hahharr.cardcollection.repository.interfaces.PackRepository;
import ru.hahharr.cardcollection.utils.OffsetLimitPage;
import ru.hahharr.cardcollection.utils.mapper.EntityFromOrmMapper;
import ru.hahharr.cardcollection.utils.mapper.ViewFromOrmMapper;

import java.util.List;
import java.util.Optional;

@Service
public class CollectionRepoService {

    @Autowired
    private CollectionRepository collectionRepository;

    @Autowired
    private CardRepository cardRepository;

    @Autowired
    private PackRepository packRepository;

    public ResponseEntity<HttpStatus> saveNewCollection(String collectionName) {
        if (collectionRepository.existsByName(collectionName)) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        CollectionOrm newCollectionOrm = new CollectionOrm();
        newCollectionOrm.setName(collectionName);
        collectionRepository.save(newCollectionOrm);

        return new ResponseEntity<>(HttpStatus.OK);
    }

    public ResponseEntity<CollectionViewResponseDto> getAllCollections(int offset, int limit) {
        Page<CollectionOrm> collectionOrmPage = collectionRepository.findAll(OffsetLimitPage.of(offset, limit));
        CollectionViewResponseDto collectionViewResponseDto = new CollectionViewResponseDto(
                collectionOrmPage
                        .map(ViewFromOrmMapper::mapCollection)
                        .stream()
                        .toList(), collectionOrmPage.getTotalPages());
        return new ResponseEntity<>(collectionViewResponseDto, HttpStatus.OK);
    }

    public ResponseEntity<PackViewResponseDto> getPacksFromCollection(CollectionId collectionId,
                                                                      int offset, int limit) {
        Optional<CollectionOrm> collectionOrm = getCollection(collectionId);
        if (collectionOrm.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        List<PackId> packIdList = EntityFromOrmMapper.mapCollection(collectionOrm.get()).getPackIdList();
        Page<PackOrm> packOrmPage = packRepository.findByIdIn(packIdList,
                OffsetLimitPage.of(offset, limit));

        PackViewResponseDto packViewResponseDto = new PackViewResponseDto(
                packOrmPage.stream()
                        .map(ViewFromOrmMapper::mapPack)
                        .toList(), packOrmPage.getTotalPages());

        return new ResponseEntity<>(packViewResponseDto, HttpStatus.OK);

    }

    public ResponseEntity<CardListResponseDto> getCardsFromCollection(CollectionId collectionId,
                                                                      int offset, int limit) {
        Optional<CollectionOrm> collectionOrm = getCollection(collectionId);
        if (collectionOrm.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        List<CardId> cardIdList = EntityFromOrmMapper.mapCollection(collectionOrm.get()).getCardIdList();
        Page<CardOrm> cardOrmPage = cardRepository.findByIdIn(cardIdList,
                OffsetLimitPage.of(offset, limit));

        CardListResponseDto cardListResponseDto = new CardListResponseDto(
                cardOrmPage.stream()
                        .map(EntityFromOrmMapper::mapCard)
                        .toList(), cardOrmPage.getTotalPages());

        return new ResponseEntity<>(cardListResponseDto, HttpStatus.OK);
    }

    private Optional<CollectionOrm> getCollection(CollectionId collectionId) {
        return collectionRepository.findById(collectionId);
    }
}
