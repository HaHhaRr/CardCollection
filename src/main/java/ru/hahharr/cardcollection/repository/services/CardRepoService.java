package ru.hahharr.cardcollection.repository.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import ru.hahharr.cardcollection.models.orm.CardOrm;
import ru.hahharr.cardcollection.models.orm.CollectionOrm;
import ru.hahharr.cardcollection.models.primitives.id.CardId;
import ru.hahharr.cardcollection.models.primitives.id.CollectionId;
import ru.hahharr.cardcollection.models.primitives.rarity.Rarity;
import ru.hahharr.cardcollection.repository.interfaces.CardRepository;
import ru.hahharr.cardcollection.repository.interfaces.CollectionRepository;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class CardRepoService {

    @Autowired
    private CardRepository cardRepository;

    @Autowired
    private CollectionRepository collectionRepository;

    public ResponseEntity<HttpStatus> saveNewCard(String cardName, CollectionId collectionId,
                                                  Rarity rarity, String url) {

        Optional<CollectionOrm> collection = collectionRepository.findById(collectionId);
        if (collection.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        CardOrm newCardOrm = new CardOrm(
                cardName,
                url,
                rarity,
                collection.get());
        cardRepository.save(newCardOrm);

        return new ResponseEntity<>(HttpStatus.OK);
    }

    public long countAllRows() {
        return cardRepository.count();
    }
}
