package ru.hahharr.cardcollection.utils.mapper;

import org.springframework.stereotype.Service;
import ru.hahharr.cardcollection.models.orm.CollectionOrm;
import ru.hahharr.cardcollection.models.orm.PackOrm;
import ru.hahharr.cardcollection.models.view.CollectionView;
import ru.hahharr.cardcollection.models.view.PackView;

@Service
public class ViewFromOrmMapper {

    public static PackView mapPack(PackOrm packOrm) {
        return EntityToViewMapper.mapPack(EntityFromOrmMapper.mapPack(packOrm));
    }

    public static CollectionView mapCollection(CollectionOrm collectionOrm) {
        return EntityToViewMapper.mapCollection(EntityFromOrmMapper.mapCollection(collectionOrm));
    }
}
