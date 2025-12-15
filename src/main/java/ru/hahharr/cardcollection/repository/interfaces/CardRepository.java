package ru.hahharr.cardcollection.repository.interfaces;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.hahharr.cardcollection.models.entity.Card;
import ru.hahharr.cardcollection.models.primitives.id.CardId;
import ru.hahharr.cardcollection.models.primitives.id.CollectionId;

import java.util.List;

@Repository
public interface CardRepository extends JpaRepository<Card, CardId> {
    List<Card> findByCollectionId(CollectionId collectionId);
}
