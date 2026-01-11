package ru.hahharr.cardcollection.utils.provider;

import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.ZoneId;

@Component
public class LocalDateTimeProvider {

    public static LocalDateTime moscow() {
        return LocalDateTime.now(ZoneId.of("Europe/Moscow"));
    }
}
