package ru.hahharr.cardcollection.models.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import ru.hahharr.cardcollection.models.entity.Card;

@Data
@AllArgsConstructor
@EqualsAndHashCode
public class UserCardCollectionDto {

    private Card card;

    private long copy;
}
