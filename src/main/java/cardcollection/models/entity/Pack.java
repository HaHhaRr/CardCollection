package cardcollection.models.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

import java.util.List;

@Entity
@Data
@Table(name = "pack")
public class Pack {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long packId;

    @Column(name = "name", nullable = false, unique = true)
    @NotBlank(message = "Поле packName не может быть пустым")
    private String packName;

    @Column(name = "cost", nullable = false)
    @Pattern(regexp = "\\d+", message = "Поле cost должно содержать число")
    private int cost;

    @ManyToOne
    @JoinColumn(name = "collection_collectionId", nullable = false)
    @NotNull(message = "Поле collection не может быть пустым")
    private Collection collection;

    @Column(name = "cards", nullable = false)
    @NotNull(message = "Поле cards не может быть пустым")
    private List<Long> cards;
}
