package cardcollection.models.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.util.List;

@Entity
@Data
@Table(name = "collection")
public class Collection {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long collectionId;

    @Column(name = "name", nullable = false, unique = true)
    @NotBlank(message = "Поле collectionName не может быть пустым")
    private String collectionName;

    @JsonBackReference
    @OneToMany(mappedBy = "collection")
    @NotNull(message = "Поле cards не может быть пустым")
    private List<Card> cards;
}
