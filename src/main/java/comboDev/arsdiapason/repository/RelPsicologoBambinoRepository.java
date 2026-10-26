package comboDev.arsdiapason.repository;

import comboDev.arsdiapason.model.RelPsicologoBambino;
import comboDev.arsdiapason.model.RelPsicologoBambinoId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RelPsicologoBambinoRepository extends JpaRepository<RelPsicologoBambino, RelPsicologoBambinoId> {

    void deleteByIdBambino(Integer idBambino);

}