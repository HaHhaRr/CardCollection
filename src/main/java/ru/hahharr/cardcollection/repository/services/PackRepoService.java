package ru.hahharr.cardcollection.repository.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
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

    public ResponseEntity<HttpStatus> saveNewPack(String packName, long collectionId, int cost,
                                                  double epicDropChance, double rareDropChance, List<Long> listIds) {
        if (!collectionRepository.existsById(new CollectionId(collectionId))
                || !cardRepoService.findCardIdsByCollection(collectionId).equals(new HashSet<>(listIds))
                || cost < 0
                || epicDropChance >= rareDropChance
                || (epicDropChance + rareDropChance) >= 1) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        List<CardId> cardIdList = listIds.stream()
                .map(CardId::new)
                .toList();

        Pack newPack = Pack.builder()
                .name(packName)
                .cost(cost)
                .collection(collectionRepository.findById(new CollectionId(collectionId)).get())
                .cards(cardIdList)
                .epicDropChance(epicDropChance)
                .rareDropChance(rareDropChance)
                .commonDropChance(1 - epicDropChance - rareDropChance)
                .build();
        packRepository.save(newPack);

        return new ResponseEntity<>(HttpStatus.OK);
    }
}
