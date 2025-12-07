package ru.hahharr.cardcollection.repository.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import ru.hahharr.cardcollection.models.entity.DropChance;
import ru.hahharr.cardcollection.models.entity.Pack;
import ru.hahharr.cardcollection.models.primitives.id.CardId;
import ru.hahharr.cardcollection.models.primitives.id.CollectionId;
import ru.hahharr.cardcollection.repository.interfaces.CollectionRepository;
import ru.hahharr.cardcollection.repository.interfaces.PackRepository;

import java.util.HashSet;
import java.util.List;

@Service
public class PackRepoService {

    @Autowired
    private PackRepository packRepository;

    @Autowired
    private CollectionRepository collectionRepository;

    @Autowired
    private CardRepoService cardRepoService;

    public ResponseEntity<HttpStatus> saveNewPack(String packName, CollectionId collectionId, int cost,
                                                  int epicDropChance, int rareDropChance, int commonDropChance,
                                                  List<Long> listIds) {
        if (isValidParam(collectionId, cost, epicDropChance,
                rareDropChance, commonDropChance, listIds)) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        List<CardId> cardIdList = listIds.stream()
                .map(CardId::new)
                .toList();

        Pack newPack = Pack.builder()
                .name(packName)
                .cost(cost)
                .collection(collectionRepository.findById(collectionId).get())
                .cards(cardIdList)
                .build();
        newPack.setDropChance(DropChance.builder()
                .commonDropChance(commonDropChance)
                .rareDropChance(rareDropChance)
                .epicDropChance(epicDropChance)
                .pack(newPack)
                .build());
        packRepository.save(newPack);

        return new ResponseEntity<>(HttpStatus.OK);
    }

    private boolean isValidParam(CollectionId collectionId, int cost, int epicDropChance,
                                 int rareDropChance, int commonDropChance, List<Long> listIds) {
        return !collectionRepository.existsById(collectionId)
                || !cardRepoService.findCardIdsByCollection(collectionId).containsAll(new HashSet<>(listIds))
                || cost < 0
                || epicDropChance >= rareDropChance
                || rareDropChance >= commonDropChance
                || (epicDropChance + rareDropChance + commonDropChance) > 100;
    }
}
