package comboDev.arsdiapason.repository;

import comboDev.arsdiapason.model.RelIstitutoBambino;
import comboDev.arsdiapason.model.RelIstitutoBambinoId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RelIstitutoBambinoRepository extends JpaRepository<RelIstitutoBambino, RelIstitutoBambinoId> {

    @Query("SELECT DISTINCT r.classe FROM RelIstitutoBambino r WHERE r.idIstituto = :idIstituto ORDER BY r.classe")
    List<Integer> findDistinctClassiByIdIstituto(@Param("idIstituto") Integer idIstituto);

    @Query("SELECT DISTINCT r.sezione FROM RelIstitutoBambino r WHERE r.idIstituto = :idIstituto ORDER BY r.sezione")
    List<String> findDistinctSezioniByIdIstituto(@Param("idIstituto") Integer idIstituto);

    void deleteByIdBambino(Integer idBambino);

}