package ru.hahharr.cardcollection.repository;

import ru.hahharr.cardcollection.models.entity.User;
import ru.hahharr.cardcollection.models.primitives.id.UserId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, UserId> {
}
