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
import ru.hahharr.cardcollection.models.primitives.id.UserId;

import java.time.LocalDateTime;

@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@EqualsAndHashCode
@Table(name = "userCoinState")
public class UserCoinStateOrm {

    @Id
    @Column(name = "user_id")
    private UserId id;

    @Column(name = "totalCoins", nullable = false)
    private int totalCoins;

    @Column(name = "available", nullable = false)
    private boolean availableFree;

    @Column(name = "lastReceived", nullable = false)
    private LocalDateTime lastReceived;

    @OneToOne
    @MapsId
    @JsonBackReference(value = JsonReference.USER_TO_COIN_STATE_REFERENCE)
    @JoinColumn(name = "user_id")
    private UserOrm userOrm;

    public UserCoinStateOrm(int totalCoins, boolean availableFree, LocalDateTime lastReceived, UserOrm userOrm) {
        this.totalCoins = totalCoins;
        this.availableFree = availableFree;
        this.lastReceived = lastReceived;
        this.userOrm = userOrm;
    }
}
