package ru.hahharr.cardcollection.models.view;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class PackView {

    private Long packId;

    private String packName;

    private int cost;

    private Long collectionId;
}
