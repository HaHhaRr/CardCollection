package ru.hahharr.cardcollection.repository;

import ru.hahharr.cardcollection.models.entity.User;
import ru.hahharr.cardcollection.models.primitives.id.UserId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, UserId> {
    Optional<User> findByUsername(String username);
}
