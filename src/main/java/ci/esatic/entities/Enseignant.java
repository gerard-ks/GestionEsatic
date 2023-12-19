package ci.esatic.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@Data @NoArgsConstructor @AllArgsConstructor
public class Enseignant {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotNull
    @NotEmpty
    private String nom;
    @NotNull
    @NotEmpty
    private String prenom;
    @Enumerated(EnumType.STRING)
    private Genre genre;
    @NotNull
    @NotEmpty
    @Email(message = "L'adresse e-mail doit être une adresse e-mail valide.")
    private String email;
    @Pattern(
            regexp = "^\\+?[0-9]*$",
            message = "Le numéro de téléphone doit contenir uniquement des chiffres et peut commencer par un '+'."
    )
    private String telephone;
    @NotNull
    @NotEmpty
    private String specialite;
    @NotNull
    @NotEmpty
    private String grade;

    @OneToMany(mappedBy = "enseignant")
    private List<EnseignantEcueAnnee> enseignantEcueAnnees = new ArrayList<>();

    @OneToMany(mappedBy = "enseignant")
    private List<Affecte> affectes = new ArrayList<>();

    @OneToOne(cascade = CascadeType.ALL)
    private AppUser appUser;

}
