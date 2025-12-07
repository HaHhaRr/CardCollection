package ru.hahharr.cardcollection.repository.interfaces;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.hahharr.cardcollection.models.entity.DropChance;
import ru.hahharr.cardcollection.models.primitives.id.PackId;

@Repository
public interface DropChanceRepository extends JpaRepository<DropChance, PackId> {
}
