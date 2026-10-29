package comboDev.arsdiapason.repository;

import comboDev.arsdiapason.model.RelBambinoScheda;
import comboDev.arsdiapason.model.RelBambinoSchedaId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RelBambinoSchedaRepository extends JpaRepository<RelBambinoScheda, RelBambinoSchedaId> {

    List<RelBambinoScheda> findByIdBambino(Integer idBambino);

    void deleteByIdBambino(Integer idBambino);
}