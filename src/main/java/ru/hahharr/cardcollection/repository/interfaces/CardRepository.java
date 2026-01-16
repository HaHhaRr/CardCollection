package ru.hahharr.cardcollection.repository.interfaces;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ru.hahharr.cardcollection.models.orm.CardOrm;
import ru.hahharr.cardcollection.models.orm.CollectionOrm;
import ru.hahharr.cardcollection.models.primitives.id.CardId;

import java.util.List;
import java.util.Set;

@Repository
public interface CardRepository extends JpaRepository<CardOrm, CardId> {
    @Query(value = "SELECT id FROM CardOrm WHERE collectionOrm = :collectionOrm")
    Set<CardId> findCardIdsByCollectionOrm(@Param("collectionOrm") CollectionOrm collectionOrm);

    Page<CardOrm> findByIdIn(List<CardId> ids, Pageable pageable);
}
