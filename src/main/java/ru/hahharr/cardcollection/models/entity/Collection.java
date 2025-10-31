package ru.hahharr.cardcollection.models.entity;

import ru.hahharr.cardcollection.models.primitives.CustomId;
import ru.hahharr.cardcollection.models.primitives.id.CollectionId;
import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

import java.util.List;

@Entity
@Data
@Table(name = "collection")
public class Collection {

    @Id
    @CustomId
    private CollectionId id;

    @Column(name = "name", nullable = false, unique = true)
    @NotBlank(message = "Поле collectionName не может быть пустым")
    private String name;

    @JsonBackReference
    @OneToMany(mappedBy = "collection")
    private List<Card> cards;
}
