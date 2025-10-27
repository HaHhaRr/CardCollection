package ru.hahharr.cardcollection.models.entity;

import ru.hahharr.cardcollection.models.primitives.CustomId;
import ru.hahharr.cardcollection.models.primitives.id.UserId;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

import java.util.List;

@Entity
@Data
@Table(name = "userCollection")
public class UserCollection {

    @Id
    @CustomId
    private UserId id;

    @Column(name = "cards", nullable = false)
    private List<Long> cards;
}
