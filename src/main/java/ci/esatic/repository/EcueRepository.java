package ci.esatic.repository;

import ci.esatic.entities.Ecue;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface EcueRepository extends JpaRepository<Ecue, Long> {
    List<Ecue>findByUe_Id(Long id);
}
