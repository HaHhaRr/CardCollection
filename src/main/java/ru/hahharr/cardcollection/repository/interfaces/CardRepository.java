package ru.hahharr.cardcollection.repository.interfaces;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.hahharr.cardcollection.models.orm.CardOrm;
import ru.hahharr.cardcollection.models.primitives.id.CardId;

@Repository
public interface CardRepository extends JpaRepository<CardOrm, CardId> {
}
