package ru.hahharr.cardcollection.repository.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import ru.hahharr.cardcollection.models.orm.CardOrm;
import ru.hahharr.cardcollection.models.orm.CollectionOrm;
import ru.hahharr.cardcollection.models.orm.DropChanceOrm;
import ru.hahharr.cardcollection.models.orm.PackOrm;
import ru.hahharr.cardcollection.models.primitives.id.CardId;
import ru.hahharr.cardcollection.models.primitives.id.CollectionId;
import ru.hahharr.cardcollection.repository.interfaces.CardRepository;
import ru.hahharr.cardcollection.repository.interfaces.CollectionRepository;
import ru.hahharr.cardcollection.repository.interfaces.PackRepository;
import ru.hahharr.cardcollection.models.dto.AddPackDto;

import java.io.IOException;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class PackRepoService {

    @Autowired
    private PackRepository packRepository;

    @Autowired
    private CollectionRepository collectionRepository;

    @Autowired
    private CardRepository cardRepository;

    public ResponseEntity<HttpStatus> saveNewPack(AddPackDto addPackDto) throws IOException {

        if (isValidParam(addPackDto.getCollectionId(),
                addPackDto.getCost(),
                addPackDto.getListIds())) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        CollectionOrm collectionOrm = collectionRepository.findById(addPackDto.getCollectionId()).get();

        PackOrm newPackOrm = new PackOrm(addPackDto.getPackName(),
                addPackDto.getCost(),
                addPackDto.getListIds(),
                collectionOrm);

        newPackOrm.setDropChanceOrm(DropChanceOrm.createFromChances(
                addPackDto.getCommonDropChance(),
                addPackDto.getRareDropChance(),
                addPackDto.getEpicDropChance(),
                newPackOrm));
        packRepository.save(newPackOrm);

        return new ResponseEntity<>(HttpStatus.OK);
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
