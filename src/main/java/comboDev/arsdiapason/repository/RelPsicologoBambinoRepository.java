package comboDev.arsdiapason.repository;

import comboDev.arsdiapason.model.RelPsicologoBambino;
import comboDev.arsdiapason.model.RelPsicologoBambinoId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RelPsicologoBambinoRepository extends JpaRepository<RelPsicologoBambino, RelPsicologoBambinoId> {

    void deleteByIdBambino(Integer idBambino);

    List<RelPsicologoBambino> findByidPsicologo(Integer idPsicologo);

}