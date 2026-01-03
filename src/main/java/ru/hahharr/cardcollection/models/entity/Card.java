package ru.hahharr.cardcollection.models.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import ru.hahharr.cardcollection.models.primitives.id.CardId;
import ru.hahharr.cardcollection.models.primitives.id.CollectionId;
import ru.hahharr.cardcollection.models.primitives.rarity.Rarity;

@Data
@AllArgsConstructor
public class Card {

    private CardId id;

    private String name;

    private String imageUrl;

    private Rarity rarity;

    private CollectionId collectionId;
}
