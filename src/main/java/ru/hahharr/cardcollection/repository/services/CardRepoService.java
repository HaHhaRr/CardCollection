package ru.hahharr.cardcollection.repository.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import ru.hahharr.cardcollection.models.entity.Card;
import ru.hahharr.cardcollection.models.orm.CardOrm;
import ru.hahharr.cardcollection.models.orm.CollectionOrm;
import ru.hahharr.cardcollection.models.primitives.id.CardId;
import ru.hahharr.cardcollection.models.primitives.id.CollectionId;
import ru.hahharr.cardcollection.models.primitives.rarity.Rarity;
import ru.hahharr.cardcollection.repository.interfaces.CardRepository;
import ru.hahharr.cardcollection.repository.interfaces.CollectionRepository;
import ru.hahharr.cardcollection.utils.mapper.EntityFromOrmMapper;

import java.util.Optional;

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
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        CardOrm newCardOrm = new CardOrm(
                cardName,
                url,
                rarity,
                collection.get());
        cardRepository.save(newCardOrm);

        return new ResponseEntity<>(HttpStatus.OK);
    }

    public Card getCard(CardId cardId) throws NullPointerException {
        Optional<CardOrm> cardOrm = cardRepository.findById(cardId);
        return cardOrm.map(EntityFromOrmMapper::mapCard)
                .orElseThrow(NullPointerException::new);
    }

    public long countAllRows() {
        return cardRepository.count();
    }
}
