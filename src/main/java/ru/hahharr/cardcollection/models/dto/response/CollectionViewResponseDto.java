package ru.hahharr.cardcollection.models.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import ru.hahharr.cardcollection.models.view.CollectionView;

import java.util.List;

@Data
@AllArgsConstructor
public class CollectionViewResponseDto {

    private List<CollectionView> collectionViewList;

    private int totalPage;
}
