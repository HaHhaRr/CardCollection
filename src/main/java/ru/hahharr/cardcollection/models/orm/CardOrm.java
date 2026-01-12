package ru.hahharr.cardcollection.models.orm;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ru.hahharr.cardcollection.models.primitives.CustomId;
import ru.hahharr.cardcollection.models.primitives.id.CardId;
import ru.hahharr.cardcollection.models.primitives.rarity.Rarity;
import ru.hahharr.cardcollection.utils.JsonReference;

@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@EqualsAndHashCode
@Table(name = "Card")
public class CardOrm {

    @Id
    @CustomId
    private CardId id;

    @Column(name = "name", nullable = false, unique = true)
    @NotBlank(message = "Поле cardName не может быть пустым")
    private String name;

    @Column(name = "imageUrl", nullable = false, unique = true)
    @NotBlank(message = "Поле imageUrl не может быть пустым")
    private String imageUrl;

    @Column(name = "rarity", nullable = false)
    @NotNull(message = "Поле rarity не может быть пустым")
    private Rarity rarity;

    @ManyToOne
    @JoinColumn(name = "collectionId", nullable = false)
    @JsonBackReference(value = JsonReference.COLLECTION_TO_CARDS_REFERENCE)
    @NotNull(message = "Поле collection не может быть пустым")
    private CollectionOrm collectionOrm;

    public CardOrm(String name, String imageUrl, Rarity rarity, CollectionOrm collectionOrm) {
        this.name = name;
        this.imageUrl = imageUrl;
        this.rarity = rarity;
        this.collectionOrm = collectionOrm;
    }
}
