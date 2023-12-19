package ci.esatic.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@Data @NoArgsConstructor @AllArgsConstructor
public class Niveau {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    private String nom;

    @ManyToOne
    @JoinColumn(name = "filiere_id")
    private Filiere filiere;

    @OneToMany
    @JoinColumn(name = "niveau_id")
    private List<Ue> ues = new ArrayList<>();

    @OneToMany
    @JoinColumn(name = "niveau_id")
    private List<Inscription> inscriptions = new ArrayList<>();

    @Override
    public String toString(){
        return nom;
    }
}
