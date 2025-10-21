package cardcollection.models.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Entity
@Data
@Table(name = "userCoinState")
public class UserCoinState {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

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
