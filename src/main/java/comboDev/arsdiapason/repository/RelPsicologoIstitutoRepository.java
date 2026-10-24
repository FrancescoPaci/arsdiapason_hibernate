package comboDev.arsdiapason.repository;

import comboDev.arsdiapason.model.RelPsicologoIstituto;
import comboDev.arsdiapason.model.RelPsicologoIstitutoId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface RelPsicologoIstitutoRepository extends JpaRepository<RelPsicologoIstituto, RelPsicologoIstitutoId> {

    // Trova tutte le relazioni associate a uno psicologo
    List<RelPsicologoIstituto> findByIdPsicologo(Integer idPsicologo);
}