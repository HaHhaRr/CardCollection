package ru.hahharr.cardcollection.models.orm;

import com.fasterxml.jackson.annotation.JsonBackReference;
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
import ru.hahharr.cardcollection.models.primitives.id.PackId;
import ru.hahharr.cardcollection.utils.JsonReference;

@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@EqualsAndHashCode
@Table(name = "drop_chance")
public class DropChanceOrm {

    private static final String WRONG_DROP_CHANCE_MESSAGE = "Wrong DropChance value";

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
    @JsonBackReference(value = JsonReference.PACK_TO_DROP_CHANCE_REFERENCE)
    @JoinColumn(name = "pack_id")
    private PackOrm packOrm;

    private DropChanceOrm(int commonDropChance, int rareDropChance, int epicDropChance, PackOrm packOrm) {
        this.commonDropChance = commonDropChance;
        this.rareDropChance = rareDropChance;
        this.epicDropChance = epicDropChance;
        this.packOrm = packOrm;
    }

    public static DropChanceOrm createFromChances(int commonDropChance,
                                                  int rareDropChance,
                                                  int epicDropChance,
                                                  PackOrm packOrm) {

        if (epicDropChance < 0
                || rareDropChance < 0
                || commonDropChance < 0
                || epicDropChance >= rareDropChance
                || rareDropChance >= commonDropChance
                || (epicDropChance + rareDropChance) < 100
                || (epicDropChance + rareDropChance + commonDropChance) > 100) {
            throw new IllegalArgumentException(WRONG_DROP_CHANCE_MESSAGE);
        }
        return new DropChanceOrm(commonDropChance, rareDropChance, epicDropChance, packOrm);
    }

    public double getCommonDropChance() {
        return (double) commonDropChance / 100;
    }

    public double getRareDropChance() {
        return (double) rareDropChance / 100;
    }

    public double getEpicDropChance() {
        return (double) epicDropChance / 100;
    }
}
