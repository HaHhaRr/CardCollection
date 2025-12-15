package ru.hahharr.cardcollection.repository.interfaces;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.hahharr.cardcollection.models.entity.Collection;
import ru.hahharr.cardcollection.models.primitives.id.CollectionId;

@Repository
public interface CollectionRepository extends JpaRepository<Collection, CollectionId> {
    boolean existsByName(String name);
}
