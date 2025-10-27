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
public class UserCollectionId implements Serializable {

    @Serial
    private static final long serialVersionUID = 6075817032143709472L;

    private Long id;

    public UserCollectionId(Long id) {
        this.id = id;
    }
}
