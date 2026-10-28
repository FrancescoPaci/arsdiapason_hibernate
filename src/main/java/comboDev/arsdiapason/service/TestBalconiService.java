package comboDev.arsdiapason.service;

import comboDev.arsdiapason.dto.DatiSchede;
import comboDev.arsdiapason.dto.TestBalconi;
import comboDev.arsdiapason.model.Bambino;
import comboDev.arsdiapason.model.RelBambinoScheda;
import comboDev.arsdiapason.repository.BambinoRepository;
import comboDev.arsdiapason.repository.ProvaSchedaRepository;
import comboDev.arsdiapason.repository.RelBambinoSchedaRepository;
import comboDev.arsdiapason.repository.SchedaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class TestBalconiService {

    @Autowired
    private BambinoRepository bambinoRepository;
    @Autowired
    private SchedaRepository schedaRepository;
    @Autowired
    private ProvaSchedaRepository provaSchedaRepository;
    @Autowired
    private RelBambinoSchedaRepository relBambinoSchedaRepository;

    @Transactional(readOnly = true)
    public DatiSchede getDatiSchede(Integer idBambino) {
        DatiSchede datiSchede = new DatiSchede();

        if (idBambino != null) {
            Optional<Bambino> bambinoOpt = bambinoRepository.findById(idBambino);
            bambinoOpt.ifPresent(datiSchede::setBambino);
            datiSchede.setRelBambinoSchede(relBambinoSchedaRepository.findByIdBambino(idBambino));
        }
        datiSchede.setSchede(schedaRepository.findAll());
        datiSchede.setProveSchede(provaSchedaRepository.findAll());

        return datiSchede;
    }

    @Transactional(rollbackFor = Exception.class)
    public void saveTest(TestBalconi testBalconi) {
        // 1. Salva le relazioni tra il bambino e le prove scelte
        for (Byte idProvaScheda : testBalconi.getIdsProvaSceda()) {
            RelBambinoScheda relBambinoScheda = new RelBambinoScheda();
            relBambinoScheda.setIdBambino(testBalconi.getBambino().getId());
            relBambinoScheda.setIdProvaScheda(idProvaScheda);
            relBambinoSchedaRepository.save(relBambinoScheda);
        }

        // 2. Aggiorna lo stato del bambino (test finito e data del test)
        Bambino bambino = testBalconi.getBambino();
        bambino.setTestFinito(true);

        // save() in JPA aggiorna il record esistente se l'ID è presente
        bambinoRepository.save(bambino);
    }
}