package ru.hahharr.cardcollection.repository.interfaces;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.hahharr.cardcollection.models.entity.UserCollection;
import ru.hahharr.cardcollection.models.primitives.id.UserId;

@Repository
public interface UserCollectionRepository extends JpaRepository<UserCollection, UserId> {
}
