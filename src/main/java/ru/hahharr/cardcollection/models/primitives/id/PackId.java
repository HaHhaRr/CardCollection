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
public class PackId implements Serializable {

    @Serial
    private static final long serialVersionUID = -6799128835498378423L;

    private Long id;

    public PackId(Long id) {
        this.id = id;
    }
}
