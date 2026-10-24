package comboDev.arsdiapason.repository;

import comboDev.arsdiapason.model.Utente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UtenteRepository extends JpaRepository<Utente, String> {

    // Esempio di query method personalizzato (Spring la implementa da sola leggendo il nome del metodo)
    Optional<Utente> findByEmail(String email);
}