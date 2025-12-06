package ru.hahharr.cardcollection.models.entity;

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

@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@EqualsAndHashCode
@Table(name = "Card")
public class Card {

    @Id
    @CustomId
    private CardId id;

    @Column(name = "name", nullable = false, unique = true)
    @NotBlank(message = "Поле cardName не может быть пустым")
    private String name;

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
