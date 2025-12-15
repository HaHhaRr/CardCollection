package ru.hahharr.cardcollection.repository.interfaces;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.hahharr.cardcollection.models.entity.UserCoinState;
import ru.hahharr.cardcollection.models.primitives.id.UserId;

@Repository
public interface UserCoinStateRepository extends JpaRepository<UserCoinState, UserId> {
}
