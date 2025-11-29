package ru.hahharr.cardcollection.models.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;
import jakarta.persistence.PrimaryKeyJoinColumn;
import jakarta.persistence.Table;
import lombok.Data;
import ru.hahharr.cardcollection.models.primitives.CustomId;
import ru.hahharr.cardcollection.models.primitives.JsonReference;
import ru.hahharr.cardcollection.models.primitives.id.UserId;
import ru.hahharr.cardcollection.security.Role;

@Entity
@Data
@Table(name = "users")
public class User {

    @Id
    @CustomId
    @Column(name = "id")
    private UserId id;

    private String username;

    private String password;

    @Enumerated(EnumType.STRING)
    private Role role;

    @OneToOne(mappedBy = "user", cascade = CascadeType.ALL)
    @PrimaryKeyJoinColumn
    @JsonBackReference(value = JsonReference.USER_TO_COLLECTION_REFERENCE)
    private UserCollection userCollection;

    @OneToOne(mappedBy = "user", cascade = CascadeType.ALL)
    @PrimaryKeyJoinColumn
    @JsonBackReference(value = JsonReference.USER_TO_COIN_STATE_REFERENCE)
    private UserCoinState userCoinState;
}
