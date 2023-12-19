package ci.esatic.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@Data @NoArgsConstructor @AllArgsConstructor
public class Ue {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    @Column(length = 45, unique = true, nullable = false)
    private String codeue;

    @NotBlank
    private String intitule;

    @Min(value = 1, message = "au moins 1")
    private int semestre;

    @Min(value = 2, message = "Au moins 2 credits")
    private int credit;

    @ManyToOne
    @JoinColumn(name = "niveau_id")
    private Niveau niveau;


    @OneToMany
    @JoinColumn(name = "ue_id")
    private List<Ecue> ecues = new ArrayList<>();
}
