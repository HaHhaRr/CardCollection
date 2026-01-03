package ru.hahharr.cardcollection.models.orm;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ru.hahharr.cardcollection.models.primitives.CustomId;
import ru.hahharr.cardcollection.utils.JsonReference;
import ru.hahharr.cardcollection.models.primitives.id.CollectionId;

import java.util.List;

@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@EqualsAndHashCode
@Table(name = "collection")
public class CollectionOrm {

    @Id
    @CustomId
    private CollectionId id;

    @Column(name = "name", nullable = false, unique = true)
    @NotBlank(message = "Поле collectionName не может быть пустым")
    private String name;

    @JsonManagedReference(value = JsonReference.COLLECTION_TO_CARDS_REFERENCE)
    @OneToMany(mappedBy = "collectionOrm", cascade = CascadeType.ALL)
    private List<CardOrm> cardOrms;

    @JsonManagedReference(value = JsonReference.COLLECTION_TO_PACKS_REFERENCE)
    @OneToMany(mappedBy = "collectionOrm", cascade = CascadeType.ALL)
    private List<PackOrm> packOrms;
}
