package ru.hahharr.cardcollection.models.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import ru.hahharr.cardcollection.models.entity.Card;

import java.util.List;

@Data
@AllArgsConstructor
public class OpenPackResponseDto {

    private int totalCoins;

    private List<Card> cardList;
}
