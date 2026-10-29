package comboDev.arsdiapason.repository;

import comboDev.arsdiapason.model.Bambino;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface BambinoRepository extends JpaRepository<Bambino, Integer> {

    Optional<Bambino> findByIdAndTestFinitoTrue(Integer id);

    @Query("SELECT b FROM Bambino b " +
            "JOIN RelPsicologoBambino rpb ON b.id = rpb.idBambino " +
            "WHERE rpb.idPsicologo = :idPsicologo")
    List<Bambino> findByPsicologoId(@Param("idPsicologo") Integer idPsicologo);

}