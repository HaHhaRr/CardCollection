package ru.hahharr.cardcollection.models.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import ru.hahharr.cardcollection.models.primitives.id.CardId;
import ru.hahharr.cardcollection.models.primitives.id.CollectionId;
import ru.hahharr.cardcollection.models.primitives.id.PackId;

import java.util.List;

@Data
@AllArgsConstructor
public class Collection {

    private CollectionId id;

    private String name;

    private List<CardId> cardIdList;

    private List<PackId> packIdList;
}
