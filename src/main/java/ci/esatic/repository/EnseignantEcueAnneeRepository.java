package ci.esatic.repository;

import ci.esatic.entities.Classe;
import ci.esatic.entities.Ecue;
import ci.esatic.entities.Enseignant;
import ci.esatic.entities.EnseignantEcueAnnee;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface EnseignantEcueAnneeRepository extends JpaRepository<EnseignantEcueAnnee, Long> {
}
