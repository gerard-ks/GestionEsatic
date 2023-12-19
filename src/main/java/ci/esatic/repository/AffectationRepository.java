package ci.esatic.repository;

import ci.esatic.entities.Affecte;
import ci.esatic.entities.Classe;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AffectationRepository extends JpaRepository<Affecte, Long> {
    List<Affecte> findByEnseignant_Id(Long id);
}
