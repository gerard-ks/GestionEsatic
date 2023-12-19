package ci.esatic.repository;

import ci.esatic.entities.AppUser;
import ci.esatic.entities.Etudiant;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EtudiantRepository extends JpaRepository<Etudiant, Long> {
    List<Etudiant> findAllByEstActif(Boolean estActif);
    List<Etudiant> findAllByEstInscrit(Boolean estInscrit);
    Etudiant findByAppUser(AppUser appUser);
}
