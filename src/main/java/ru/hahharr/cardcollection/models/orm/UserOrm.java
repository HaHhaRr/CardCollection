package ru.hahharr.cardcollection.models.orm;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ru.hahharr.cardcollection.models.primitives.CustomId;
import ru.hahharr.cardcollection.utils.JsonReference;
import ru.hahharr.cardcollection.models.primitives.id.UserId;
import ru.hahharr.cardcollection.security.Role;

@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@EqualsAndHashCode
@Table(name = "users")
public class UserOrm {

    @Id
    @CustomId
    @Column(name = "id")
    private UserId id;

    private String username;

    private String password;

    @Enumerated(EnumType.STRING)
    private Role role;

    @OneToOne(mappedBy = "userOrm", cascade = CascadeType.ALL)
    @JsonManagedReference(value = JsonReference.USER_TO_COLLECTION_REFERENCE)
    private UserCollectionOrm userCollectionOrm;

    @OneToOne(mappedBy = "userOrm", cascade = CascadeType.ALL)
    @JsonManagedReference(value = JsonReference.USER_TO_COIN_STATE_REFERENCE)
    private UserCoinStateOrm userCoinStateOrm;

    public UserOrm(String username, String password, Role role) {
        this.username = username;
        this.password = password;
        this.role = role;
    }
}
