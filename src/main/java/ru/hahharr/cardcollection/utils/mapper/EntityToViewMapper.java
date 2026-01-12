package ru.hahharr.cardcollection.utils.mapper;

import org.springframework.stereotype.Service;
import ru.hahharr.cardcollection.models.entity.Collection;
import ru.hahharr.cardcollection.models.entity.Pack;
import ru.hahharr.cardcollection.models.view.CollectionView;
import ru.hahharr.cardcollection.models.view.PackView;

@Service
public class EntityToViewMapper {

    public static PackView mapPack(Pack pack) {
        return new PackView(pack.getId().getId(),
                pack.getName(),
                pack.getCost(),
                pack.getCollectionId().getId());
    }

    public static CollectionView mapCollection(Collection collection) {
        return new CollectionView(collection.getId().getId(),
                collection.getName());
    }
}
