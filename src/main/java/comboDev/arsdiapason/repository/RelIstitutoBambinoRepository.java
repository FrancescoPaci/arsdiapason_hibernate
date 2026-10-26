package comboDev.arsdiapason.repository;

import comboDev.arsdiapason.model.RelIstitutoBambino;
import comboDev.arsdiapason.model.RelIstitutoBambinoId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RelIstitutoBambinoRepository extends JpaRepository<RelIstitutoBambino, RelIstitutoBambinoId> {

    void deleteByIdBambino(Integer idBambino);

}