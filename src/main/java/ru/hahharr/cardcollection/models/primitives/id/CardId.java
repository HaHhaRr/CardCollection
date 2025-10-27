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
public class CardId implements Serializable {

    @Serial
    private static final long serialVersionUID = -9013708682398662752L;

    private Long id;

    public CardId(Long id) {
        this.id = id;
    }
}
