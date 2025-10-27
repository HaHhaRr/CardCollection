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
public class UserId implements Serializable {

    @Serial
    private static final long serialVersionUID = -218444776313330306L;

    private Long id;

    public UserId(Long id) {
        this.id = id;
    }
}
