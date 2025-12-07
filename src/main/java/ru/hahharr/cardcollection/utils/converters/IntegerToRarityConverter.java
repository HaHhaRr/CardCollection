package ru.hahharr.cardcollection.utils.converters;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import ru.hahharr.cardcollection.models.primitives.rarity.Rarity;

@Component
public class IntegerToRarityConverter implements Converter<String, Rarity> {

    @Override
    public Rarity convert(String rarity) {
        return switch (Integer.parseInt(rarity)) {
            case 0 -> Rarity.COMMON;
            case 1 -> Rarity.RARE;
            case 2 -> Rarity.EPIC;
            default -> throw new RuntimeException("Unknown rarity id");
        };
    }
}
