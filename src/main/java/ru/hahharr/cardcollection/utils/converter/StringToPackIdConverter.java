package ru.hahharr.cardcollection.utils.converter;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import ru.hahharr.cardcollection.models.primitives.id.PackId;

@Component
public class StringToPackIdConverter implements Converter<String, PackId> {

    @Override
    public PackId convert(String id) {
        return new PackId(Long.valueOf(id));
    }
}
