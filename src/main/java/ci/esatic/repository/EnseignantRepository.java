package ci.esatic.repository;

import ci.esatic.entities.AppUser;
import ci.esatic.entities.Enseignant;
import ci.esatic.entities.Etudiant;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EnseignantRepository extends JpaRepository<Enseignant, Long> {
    Enseignant findByAppUser(AppUser appUser);
}
