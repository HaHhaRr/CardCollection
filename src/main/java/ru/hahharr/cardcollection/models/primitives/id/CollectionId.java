package ru.hahharr.cardcollection.models.primitives.id;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serial;
import java.io.Serializable;

@Setter
@Getter
@NoArgsConstructor
@EqualsAndHashCode
public class CollectionId implements Serializable {

    @Serial
    private static final long serialVersionUID = 2444474769183309845L;

    private Long id;

    public CollectionId(Long id) {
        this.id = id;
    }
}
