package comboDev.arsdiapason.service;

import comboDev.arsdiapason.model.Bambino;
import comboDev.arsdiapason.repository.BambinoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class ChildListService {

    @Autowired
    private BambinoRepository bambinoRepository;

    @Transactional(readOnly = true)
    public List<Bambino> childsList(Integer idPsicologo) {
        return bambinoRepository.findByPsicologoId(idPsicologo);
    }

}