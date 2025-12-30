package ru.hahharr.cardcollection;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import ru.hahharr.cardcollection.models.orm.DropChanceOrm;
import ru.hahharr.cardcollection.models.orm.PackOrm;

import java.io.IOException;

public class Testing {

    @Test
    public void usHundred() throws IOException {
        DropChanceOrm dropChanceOrm = DropChanceOrm.createFromChances(50,
                40,
                10,
                new PackOrm());
        Assertions.assertEquals(0.5, dropChanceOrm.getCommonDropChance());
    }
}
