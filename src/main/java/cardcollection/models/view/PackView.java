package cardcollection.models.view;

import cardcollection.models.entity.Collection;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class PackView {

    private Long packId;

    private String packName;

    private int cost;

    private Collection collection;
}
