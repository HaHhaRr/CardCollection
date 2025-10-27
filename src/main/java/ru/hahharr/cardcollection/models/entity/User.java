package ru.hahharr.cardcollection.models.entity;

import ru.hahharr.cardcollection.models.primitives.CustomId;
import ru.hahharr.cardcollection.models.primitives.id.UserId;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Data
@Table(name = "testuser")
public class User {

    @Id
    @CustomId
    private UserId id;

    private String username;

    private String password;
}
