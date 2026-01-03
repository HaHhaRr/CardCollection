package ru.hahharr.cardcollection.repository.interfaces;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.hahharr.cardcollection.models.orm.PackOrm;
import ru.hahharr.cardcollection.models.primitives.id.PackId;

@Repository
public interface PackRepository extends JpaRepository<PackOrm, PackId> {
}
