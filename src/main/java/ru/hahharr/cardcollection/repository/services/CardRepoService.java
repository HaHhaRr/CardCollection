package ru.hahharr.cardcollection.repository.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import ru.hahharr.cardcollection.models.entity.Card;
import ru.hahharr.cardcollection.models.primitives.id.CardId;
import ru.hahharr.cardcollection.models.primitives.id.CollectionId;
import ru.hahharr.cardcollection.models.primitives.rarity.Rarity;
import ru.hahharr.cardcollection.repository.interfaces.CardRepository;
import ru.hahharr.cardcollection.repository.interfaces.CollectionRepository;

import java.io.IOException;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class CardRepoService {

    @Autowired
    private CardRepository cardRepository;

    @Autowired
    private CollectionRepository collectionRepository;

    public ResponseEntity<HttpStatus> saveNewCard(String cardName, CollectionId collectionId,
                                                  Rarity rarity, String url) throws IOException {
        if (!collectionRepository.existsById(collectionId)) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        Card newCard = Card.builder()
                .name(cardName)
                .collection(collectionRepository.findById(collectionId).get())
                .rarity(rarity)
                .imageUrl(url)
                .build();
        cardRepository.save(newCard);

        return new ResponseEntity<>(HttpStatus.OK);
    }

    public Set<Long> findCardIdsByCollection(CollectionId collectionId) {
        List<Card> cardIdList = cardRepository.findByCollectionId(collectionId);
        return cardIdList.stream()
                .map(Card::getId)
                .map(CardId::getId)
                .collect(Collectors.toSet());
    }
}
