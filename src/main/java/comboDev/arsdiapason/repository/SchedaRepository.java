package comboDev.arsdiapason.repository;

import comboDev.arsdiapason.model.Scheda;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SchedaRepository extends JpaRepository<Scheda, Byte> {
    // La chiave primaria 'numero' è un Byte
}