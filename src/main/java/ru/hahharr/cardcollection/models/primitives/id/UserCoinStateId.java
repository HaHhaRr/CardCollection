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
public class UserCoinStateId implements Serializable {

    @Serial
    private static final long serialVersionUID = 4836728408913468344L;

    private Long id;

    public UserCoinStateId(Long id) {
        this.id = id;
    }
}
