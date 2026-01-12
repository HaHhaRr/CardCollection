package ru.hahharr.cardcollection.utils.converter;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import ru.hahharr.cardcollection.models.primitives.id.CardId;

@Component
public class StringToCardIdConverter implements Converter<String, CardId> {

    @Override
    public CardId convert(String id) {
        return new CardId(Long.valueOf(id));
    }
}
