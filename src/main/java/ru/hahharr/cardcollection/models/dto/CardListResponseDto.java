package ru.hahharr.cardcollection.models.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import ru.hahharr.cardcollection.models.entity.Card;

import java.util.List;

@Data
@AllArgsConstructor
public class CardListResponseDto {

    private List<Card> cardList;

    private int totalPage;
}
