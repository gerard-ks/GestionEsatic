package ci.esatic.entities;

import jakarta.persistence.*;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Data @NoArgsConstructor @AllArgsConstructor
public class AnneeAcademique {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    private String description;

    @Temporal(TemporalType.DATE)
    private Date dateDebut;

    @Temporal(TemporalType.DATE)
    private Date dateFin;

    @OneToMany
    @JoinColumn(name = "annee_academique_id")
    private List<Inscription> ins= new ArrayList<>();

    @OneToMany(mappedBy = "anneeAcademique")
    private List<EnseignantEcueAnnee> enseignantEcueAnnees = new ArrayList<>();

    @OneToMany(mappedBy = "anneeAcademique")
    private List<Affecte> affectes = new ArrayList<>();
}
