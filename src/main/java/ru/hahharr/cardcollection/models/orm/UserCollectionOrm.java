package ru.hahharr.cardcollection.models.orm;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.MapsId;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ru.hahharr.cardcollection.utils.JsonReference;
import ru.hahharr.cardcollection.models.primitives.id.CardId;
import ru.hahharr.cardcollection.models.primitives.id.UserId;

import java.util.List;

@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@EqualsAndHashCode
@Table(name = "userCollection")
public class UserCollectionOrm {

    @Id
    @Column(name = "user_id")
    private UserId id;

    @OneToOne
    @MapsId
    @JsonBackReference(value = JsonReference.USER_TO_COLLECTION_REFERENCE)
    @JoinColumn(name = "user_id")
    private UserOrm userOrm;

    @Column(name = "cards")
    private List<CardId> cards;

    public UserCollectionOrm(UserOrm userOrm, List<CardId> cards) {
        this.userOrm = userOrm;
        this.cards = cards;
    }
}
