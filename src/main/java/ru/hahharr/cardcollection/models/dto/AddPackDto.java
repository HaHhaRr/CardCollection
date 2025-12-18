package ru.hahharr.cardcollection.models.dto;

import lombok.Data;
import ru.hahharr.cardcollection.models.primitives.id.CardId;
import ru.hahharr.cardcollection.models.primitives.id.CollectionId;

import java.util.List;

@Data
public class AddPackDto {
    private String packName;

    private CollectionId collectionId;

    private int cost;

    private int epicDropChance;

    private int rareDropChance;

    private int commonDropChance;

    private List<CardId> listIds;
}
