package ci.esatic.repository;

import ci.esatic.entities.Niveau;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface NiveauRepository extends JpaRepository<Niveau, Long> {

    List<Niveau> findByFiliere_Id(Long id);

}
