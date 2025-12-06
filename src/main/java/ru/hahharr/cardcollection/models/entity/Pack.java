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
import ru.hahharr.cardcollection.models.primitives.id.PackId;

import java.util.List;

@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@EqualsAndHashCode
@Table(name = "pack")
public class Pack {

    @Id
    @CustomId
    private PackId id;

    @Column(name = "name", nullable = false, unique = true)
    @NotBlank(message = "Поле packName не может быть пустым")
    private String name;

    @Column(name = "cost", nullable = false)
    private int cost;

    @ManyToOne
    @JoinColumn(name = "collection_collectionId", nullable = false)
    @NotNull(message = "Поле collection не может быть пустым")
    private Collection collection;

    @Column(name = "cards", nullable = false)
    private List<CardId> cards;

    @Column(name = "common_drop_chance")
    private double commonDropChance;

    @Column(name = "rare_drop_chance")
    private double rareDropChance;

    @Column(name = "epic_drop_chance")
    private double epicDropChance;
}
