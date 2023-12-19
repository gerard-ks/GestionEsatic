package ci.esatic.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@Data @NoArgsConstructor @AllArgsConstructor
public class Classe {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nom;

    @OneToMany
    @JoinColumn(name = "classe_id")
    private List<Inscription> insc = new ArrayList<>();

    @OneToMany(mappedBy = "classe")
    private List<Affecte> affectes = new ArrayList<>();
}
