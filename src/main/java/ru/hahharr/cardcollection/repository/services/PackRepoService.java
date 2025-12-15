package ru.hahharr.cardcollection.repository.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import ru.hahharr.cardcollection.models.entity.Collection;
import ru.hahharr.cardcollection.models.entity.DropChance;
import ru.hahharr.cardcollection.models.entity.Pack;
import ru.hahharr.cardcollection.models.primitives.id.CardId;
import ru.hahharr.cardcollection.models.primitives.id.CollectionId;
import ru.hahharr.cardcollection.repository.interfaces.CollectionRepository;
import ru.hahharr.cardcollection.repository.interfaces.PackRepository;
import ru.hahharr.cardcollection.utils.dto.AddPackDto;

import java.io.IOException;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class PackRepoService {

    @Autowired
    private PackRepository packRepository;

    @Autowired
    private CollectionRepository collectionRepository;

    @Autowired
    private CardRepoService cardRepoService;

    public ResponseEntity<HttpStatus> saveNewPack(AddPackDto addPackDto) throws IOException {

        if (isValidParam(addPackDto.getCollectionId(),
                addPackDto.getCost(),
                addPackDto.getListIds())) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        Collection collection = collectionRepository.findById(addPackDto.getCollectionId()).get();

        Pack newPack = Pack.builder()
                .name(addPackDto.getPackName())
                .cost(addPackDto.getCost())
                .collection(collection)
                .cards(addPackDto.getListIds())
                .build();
        newPack.setDropChance(DropChance.createFromChances(
                addPackDto.getCommonDropChance(),
                addPackDto.getRareDropChance(),
                addPackDto.getEpicDropChance(),
                newPack));
        packRepository.save(newPack);

        return new ResponseEntity<>(HttpStatus.OK);
    }

    private boolean isValidParam(CollectionId collectionId, int cost, List<CardId> listIds) {
        Set<CardId> setIds = new HashSet<>(listIds);
        return !collectionRepository.existsById(collectionId)
                || !cardRepoService.findCardIdsByCollection(collectionId).containsAll(listIds)
                || setIds.size() != listIds.size()
                || cost < 0;
    }
}
