package ru.hahharr.cardcollection.models.dto.request;

import lombok.Data;
import ru.hahharr.cardcollection.models.primitives.id.CardId;
import ru.hahharr.cardcollection.models.primitives.id.CollectionId;

import java.util.List;

@Data
public class AddPackRequestDto {
    private String packName;

    private CollectionId collectionId;

    private int cost;

    private int epicDropChance;

    private int rareDropChance;

    private int commonDropChance;

    private List<CardId> listIds;
}
