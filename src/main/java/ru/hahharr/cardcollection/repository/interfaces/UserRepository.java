package ru.hahharr.cardcollection.repository.interfaces;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.hahharr.cardcollection.models.orm.UserOrm;
import ru.hahharr.cardcollection.models.primitives.id.UserId;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<UserOrm, UserId> {
    Optional<UserOrm> findByUsername(String username);
}
