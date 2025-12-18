package ru.hahharr.cardcollection.models.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import ru.hahharr.cardcollection.models.primitives.id.PackId;

@Data
@AllArgsConstructor
public class DropChance {

    private PackId id;

    private double commonDropChance;

    private double rareDropChance;

    private double epicDropChance;
}
