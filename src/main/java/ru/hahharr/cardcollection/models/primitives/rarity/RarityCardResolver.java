package ru.hahharr.cardcollection.models.primitives.rarity;

import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class RarityCardResolver {

    public Rarity resolveRarity(int rarityNum) throws IOException {
        return switch (rarityNum) {
            case 0 -> Rarity.COMMON;
            case 1 -> Rarity.RARE;
            case 2 -> Rarity.EPIC;
            default -> throw new IOException("Unknown rarity id");
        };
    }
}
