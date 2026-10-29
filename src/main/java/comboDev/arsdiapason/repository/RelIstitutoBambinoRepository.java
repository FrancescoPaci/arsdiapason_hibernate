package comboDev.arsdiapason.repository;

import comboDev.arsdiapason.model.RelIstitutoBambino;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RelIstitutoBambinoRepository extends JpaRepository<RelIstitutoBambino, Integer> {

    void deleteByIdBambino(Integer idBambino);

    List<RelIstitutoBambino> findByidBambino(Integer idBambino);

}