package ru.hahharr.cardcollection.models.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import ru.hahharr.cardcollection.models.view.PackView;

import java.util.List;

@AllArgsConstructor
@Data
public class PackViewResponseDto {

    private List<PackView> packViewList;

    private int totalPage;
}
