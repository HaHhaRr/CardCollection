package ru.hahharr.cardcollection.repository.interfaces;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.hahharr.cardcollection.models.orm.UserCollectionOrm;
import ru.hahharr.cardcollection.models.primitives.id.UserId;

@Repository
public interface UserCollectionRepository extends JpaRepository<UserCollectionOrm, UserId> {
}
