package ci.esatic.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.userdetails.User;


import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Data @NoArgsConstructor @AllArgsConstructor
public class Etudiant {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @NotEmpty
    @Pattern(regexp = "^\\d+[A-Z]+\\d+[A-Z]+$", message = "Format du numéro de matricule invalide.")
    private String numeroMatricule;

    @NotNull
    @NotEmpty
    @Size(min=4, max=20, message = "Le nom doit avoir entre 4 et 20 caractères.")
    private String nom;

    @NotNull
    @NotEmpty
    @Size(min=4, max=50, message = "Le prénom doit avoir entre 4 et 50 caractères.")
    private String prenom;

    @Temporal(TemporalType.DATE)
    private Date dateDeNaisance;

    @NotBlank
    @Size(max = 255, message = "La taille de l'adresse ne doit pas dépasser 255 caractères.")
    private String adresse;

    @Enumerated(EnumType.STRING)
    private Genre genre;

    @Pattern(
            regexp = "^\\+?[0-9]*$",
            message = "Le numéro de téléphone doit contenir uniquement des chiffres et peut commencer par un '+'."
    )
    private String numeroDeTelephone;

    @NotNull
    @NotEmpty
    @Email(message = "L'adresse e-mail doit être une adresse e-mail valide.")
    private String email;

    @NotNull
    @NotEmpty
    @Size(min=4, max=50, message = "Le nom du parent doit avoir entre 4 et 50 caractères.")
    private String parentName;

    @Pattern(
            regexp = "^\\+?[0-9]*$",
            message = "Le numéro de téléphone doit contenir uniquement des chiffres et peut commencer par un '+'."
    )
    private String telephoneParent;

    private String photoEtudiant;


    private boolean estInscrit;


    private boolean estActif;

    @OneToMany
    @JoinColumn(name = "etudiant_id")
    private List<Inscription> inscr = new ArrayList<>();


    @OneToOne(cascade = CascadeType.ALL)
    private AppUser appUser;

    @PrePersist
    protected void onCreate() {
        estActif = true;
    }
}
