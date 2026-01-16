package ru.hahharr.cardcollection.repository.interfaces;

import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ru.hahharr.cardcollection.models.orm.UserCoinStateOrm;
import ru.hahharr.cardcollection.models.primitives.id.UserId;

@Repository
public interface UserCoinStateRepository extends JpaRepository<UserCoinStateOrm, UserId> {

    @Transactional
    @Modifying
    @Query("UPDATE UserCoinStateOrm u SET u.totalCoins = u.totalCoins - :cost " +
            "WHERE u.id = :userId AND u.totalCoins >= :cost")
    int subtractCoins(@Param("userId") UserId userId, @Param("cost") int cost);
}
