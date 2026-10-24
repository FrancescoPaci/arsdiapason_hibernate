package comboDev.arsdiapason.repository;

import comboDev.arsdiapason.model.Istituto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IstitutoRepository extends JpaRepository<Istituto, Integer> {
}