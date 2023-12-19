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
public class Ecue {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    @Column(unique = true, nullable = false)
    private String codeecue;

    private String intitule;

    @ManyToOne
    @JoinColumn(name = "ue_id")
    private Ue ue;

    @OneToMany(mappedBy = "ecue")
    private List<EnseignantEcueAnnee> enseignantEcueAnnees = new ArrayList<>();
}
