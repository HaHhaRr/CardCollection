package ru.hahharr.cardcollection.repository.interfaces;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.hahharr.cardcollection.models.orm.PackOrm;
import ru.hahharr.cardcollection.models.primitives.id.PackId;

import java.util.List;

@Repository
public interface PackRepository extends JpaRepository<PackOrm, PackId> {

    Page<PackOrm> findByIdIn(List<PackId> ids, Pageable pageable);
}
