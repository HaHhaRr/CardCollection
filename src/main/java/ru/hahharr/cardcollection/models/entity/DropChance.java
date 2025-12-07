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
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ru.hahharr.cardcollection.models.primitives.CustomId;
import ru.hahharr.cardcollection.models.primitives.JsonReference;
import ru.hahharr.cardcollection.models.primitives.id.PackId;

@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@EqualsAndHashCode
@Table(name = "drop_chance")
public class DropChance {

    @Id
    @Column(name = "pack_id")
    private PackId id;

    @Column(name = "common_drop_chance")
    private int commonDropChance;

    @Column(name = "rare_drop_chance")
    private int rareDropChance;

    @Column(name = "epic_drop_chance")
    private int epicDropChance;

    @OneToOne
    @MapsId
    @JsonManagedReference(value = JsonReference.PACK_TO_DROP_CHANCE_REFERENCE)
    @JoinColumn(name = "id")
    private Pack pack;
}
