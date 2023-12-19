package ci.esatic.repository;

import ci.esatic.entities.Etudiant;
import ci.esatic.entities.Inscription;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface InscriptionRepository extends JpaRepository<Inscription, Long> {
    List<Inscription> findByClasse_Id(Long id);
}
