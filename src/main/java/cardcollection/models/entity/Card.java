package cardcollection.models.entity;

import cardcollection.models.Rarity;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Entity
@Data
@Table(name = "Card")
public class Card {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long cardId;

    @Column(name = "name", nullable = false, unique = true)
    @NotBlank(message = "Поле cardName не может быть пустым")
    private String cardName;

    @ManyToOne
    @JoinColumn(name = "collection_collectionId", nullable = false)
    @NotNull(message = "Поле collection не может быть пустым")
    private Collection collection;

    @Column(name = "imageUrl", nullable = false, unique = true)
    @NotBlank(message = "Поле imageUrl не может быть пустым")
    private String imageUrl;

    @Column(name = "rarity", nullable = false)
    @NotNull(message = "Поле rarity не может быть пустым")
    private Rarity rarity;
}
