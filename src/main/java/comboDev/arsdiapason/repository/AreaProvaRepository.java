package comboDev.arsdiapason.repository;

import comboDev.arsdiapason.model.AreaProva;
import comboDev.arsdiapason.model.AreaProvaId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AreaProvaRepository extends JpaRepository<AreaProva, AreaProvaId> {
}