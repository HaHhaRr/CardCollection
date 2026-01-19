package ru.hahharr.cardcollection.models.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class UserCardListResponseDto {

    private List<UserCardCollectionDto> cardList;

    private boolean hasNext;
}
