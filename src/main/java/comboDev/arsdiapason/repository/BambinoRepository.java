package comboDev.arsdiapason.repository;

import comboDev.arsdiapason.model.Bambino;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface BambinoRepository extends JpaRepository<Bambino, Integer> {

    // Cerca il bambino per ID solo se il test è completato
    Optional<Bambino> findByIdAndTestFinitoTrue(Integer id);
}