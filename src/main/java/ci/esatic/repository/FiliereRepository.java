package ci.esatic.repository;

import ci.esatic.entities.Filiere;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


public interface FiliereRepository extends JpaRepository<Filiere, Long> {
}
