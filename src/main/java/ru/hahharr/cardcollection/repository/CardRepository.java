package ru.hahharr.cardcollection.repository;

import ru.hahharr.cardcollection.models.entity.Card;
import ru.hahharr.cardcollection.models.primitives.id.CardId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CardRepository extends JpaRepository<Card, CardId> {
}
