package ru.hahharr.cardcollection.repository.interfaces;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.hahharr.cardcollection.models.orm.CardOrm;
import ru.hahharr.cardcollection.models.orm.UserCollectionOrm;
import ru.hahharr.cardcollection.models.primitives.id.CardId;
import ru.hahharr.cardcollection.models.primitives.id.UserId;

import java.util.List;

@Repository
public interface UserCollectionRepository extends JpaRepository<UserCollectionOrm, UserId> {

    Page<CardOrm> findByIdIn(List<CardId> ids, Pageable pageable);
}
