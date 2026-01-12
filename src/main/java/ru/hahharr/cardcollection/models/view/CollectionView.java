package ru.hahharr.cardcollection.models.view;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class CollectionView {

    private Long collectionId;

    private String collectionName;
}
