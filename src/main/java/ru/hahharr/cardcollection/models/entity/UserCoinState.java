package ru.hahharr.cardcollection.models.entity;

import ru.hahharr.cardcollection.models.primitives.CustomId;
import ru.hahharr.cardcollection.models.primitives.id.UserId;
import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

import java.time.LocalDateTime;

@Entity
@Data
@Table(name = "userCoinState")
public class UserCoinState {

    @Id
    @CustomId
    private UserId id;

    @JsonIgnore
    @Column(name = "totalCoins", nullable = false)
    private int totalCoins;

    @JsonIgnore
    @Column(name = "available", nullable = false)
    private boolean availableFree;

    @JsonIgnore
    @Column(name = "lastReceived", nullable = false)
    private LocalDateTime lastReceived;
}
