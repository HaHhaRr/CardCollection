package ru.hahharr.cardcollection.models.orm;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
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
import ru.hahharr.cardcollection.utils.JsonReference;
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
public class PackOrm {

    @Id
    @CustomId
    private PackId id;

    @Column(name = "name", nullable = false, unique = true)
    @NotBlank(message = "Поле packName не может быть пустым")
    private String name;

    @Column(name = "cost", nullable = false)
    private int cost;

    @Column(name = "cards", nullable = false)
    private List<CardId> cards;

    @ManyToOne
    @JoinColumn(name = "collectionId", nullable = false)
    @JsonBackReference(value = JsonReference.COLLECTION_TO_PACKS_REFERENCE)
    @NotNull(message = "Поле collection не может быть пустым")
    private CollectionOrm collectionOrm;

    @OneToOne(mappedBy = "packOrm", cascade = CascadeType.ALL)
    @JsonManagedReference(value = JsonReference.PACK_TO_DROP_CHANCE_REFERENCE)
    private DropChanceOrm dropChanceOrm;

    public PackOrm(String name, int cost, List<CardId> cards, CollectionOrm collectionOrm) {
        this.name = name;
        this.cost = cost;
        this.cards = cards;
        this.collectionOrm = collectionOrm;
    }
}
