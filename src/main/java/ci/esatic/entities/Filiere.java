package ci.esatic.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;


@Entity
@Data @NoArgsConstructor @AllArgsConstructor
public class Filiere {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    @Size(max = 255, message = "maximum 50 caracteres")
    @Column(unique = true, nullable = false)
    private String description;

    @NotBlank
    @Size(min = 2, message = "minimum 2 caracteres")
    @Column(length = 45, unique = true, nullable = false)
    private String sigle;

    @NotBlank
    @Size(max = 7, message = "maximum 7 caracteres")
    private String grade;

    @Min(value = 1, message = "La durée de la formation doit être d'au moins 1 ans")
    private int dureeFormation;

    @Min(value = 2, message = "Au moins 2 semestres")
    private int semestre;

/*    @OneToMany
    @JoinColumn(name = "filiere_id")
    private List<Inscription> inscri = new ArrayList<>();*/

    @OneToMany
    @JoinColumn(name = "filiere_id")
    private List<Niveau> niveaux = new ArrayList<>();

}

