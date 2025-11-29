package ru.hahharr.cardcollection.models.entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.MapsId;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.hahharr.cardcollection.models.primitives.JsonReference;
import ru.hahharr.cardcollection.models.primitives.id.UserId;

import java.time.LocalDateTime;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "userCoinState")
public class UserCoinState {

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
    @JsonManagedReference(value = JsonReference.USER_TO_COIN_STATE_REFERENCE)
    @JoinColumn(name = "user_id")
    private User user;
}
