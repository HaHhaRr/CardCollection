package ru.hahharr.cardcollection.models.view;

import ru.hahharr.cardcollection.models.entity.Collection;
import lombok.Builder;
import lombok.Data;
import ru.hahharr.cardcollection.models.primitives.id.CollectionId;

@Data
@Builder
public class PackView {

    private Long packId;

    private String packName;

    private int cost;

    private CollectionId collectionId;
}
