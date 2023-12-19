package ci.esatic.repository;

import ci.esatic.entities.Ue;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UeRepository extends JpaRepository<Ue, Long> {
    List<Ue> findByNiveau_Id(Long id);
}
