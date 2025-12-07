package ru.hahharr.cardcollection.utils.converters;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import ru.hahharr.cardcollection.models.primitives.id.CollectionId;

@Component
public class LongToCollectionIdConverter implements Converter<String, CollectionId> {

    @Override
    public CollectionId convert(String id) {
        return new CollectionId(Long.valueOf(id));
    }
}
