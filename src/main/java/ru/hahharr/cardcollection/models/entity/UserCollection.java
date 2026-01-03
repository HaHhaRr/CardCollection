package ru.hahharr.cardcollection.models.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import ru.hahharr.cardcollection.models.primitives.id.CardId;
import ru.hahharr.cardcollection.models.primitives.id.UserId;

import java.util.List;

@Data
@AllArgsConstructor
public class UserCollection {

    private UserId id;

    private List<CardId> cards;
}
