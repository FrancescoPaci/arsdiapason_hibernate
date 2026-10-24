package comboDev.arsdiapason.repository;

import comboDev.arsdiapason.model.Psicologo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PsicologoRepository extends JpaRepository<Psicologo, Integer> {
}