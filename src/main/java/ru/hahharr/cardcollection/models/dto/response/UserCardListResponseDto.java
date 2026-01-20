package ru.hahharr.cardcollection.models.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import ru.hahharr.cardcollection.models.dto.UserCardCollectionDto;

import java.util.List;

@Data
@AllArgsConstructor
public class UserCardListResponseDto {

    private List<UserCardCollectionDto> cardList;

    private boolean hasNext;
}
