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
import ru.hahharr.cardcollection.models.primitives.JsonReference;
import ru.hahharr.cardcollection.models.primitives.id.PackId;

import java.io.IOException;

@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@EqualsAndHashCode
@Table(name = "drop_chance")
public class DropChance {

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
    @JsonManagedReference(value = JsonReference.PACK_TO_DROP_CHANCE_REFERENCE)
    @JoinColumn(name = "id")
    private Pack pack;

    private DropChance(int commonDropChance, int rareDropChance, int epicDropChance, Pack pack) {
        this.commonDropChance = commonDropChance;
        this.rareDropChance = rareDropChance;
        this.epicDropChance = epicDropChance;
        this.pack = pack;
    }

    public static DropChance createFromChances(int commonDropChance,
                                               int rareDropChance,
                                               int epicDropChance,
                                               Pack pack) throws IOException {

        if (epicDropChance < 0
                || rareDropChance < 0
                || commonDropChance < 0
                || epicDropChance >= rareDropChance
                || rareDropChance >= commonDropChance
                || (epicDropChance + rareDropChance + commonDropChance) > 100) {
            throw new IOException(WRONG_DROP_CHANCE_MESSAGE);
        }
        return new DropChance(commonDropChance, rareDropChance, epicDropChance, pack);
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
