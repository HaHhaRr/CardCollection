package ru.hahharr.cardcollection.models.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import ru.hahharr.cardcollection.models.primitives.id.UserId;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
public class UserCoinState {

    private UserId id;

    private int totalCoins;

    private boolean availableFree;

    private LocalDateTime freeCoinsTime;
}
