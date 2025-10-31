package ru.hahharr.cardcollection.repository;

import ru.hahharr.cardcollection.models.entity.Collection;
import ru.hahharr.cardcollection.models.primitives.id.CollectionId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CollectionRepository extends JpaRepository<Collection, CollectionId> {
}
