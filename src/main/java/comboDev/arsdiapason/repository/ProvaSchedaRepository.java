package comboDev.arsdiapason.repository;

import comboDev.arsdiapason.model.ProvaScheda;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProvaSchedaRepository extends JpaRepository<ProvaScheda, Byte> {

    // Esempio per recuperare tutte le prove collegate a una specifica scheda
    List<ProvaScheda> findByNumeroScheda(Byte numeroScheda);
}