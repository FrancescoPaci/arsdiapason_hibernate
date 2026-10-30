package comboDev.arsdiapason.service;

import comboDev.arsdiapason.model.Bambino;
import comboDev.arsdiapason.model.RelPsicologoBambino;
import comboDev.arsdiapason.repository.BambinoRepository;
import comboDev.arsdiapason.repository.RelBambinoSchedaRepository;
import comboDev.arsdiapason.repository.RelPsicologoBambinoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class InsertChildService {

    @Autowired
    private BambinoRepository bambinoRepository;
    @Autowired
    private RelPsicologoBambinoRepository relPsicologoBambinoRepository;
    @Autowired
    private RelBambinoSchedaRepository relBambinoSchedaRepository;

    @Transactional(rollbackFor = Exception.class)
    public void insertChild(Bambino bambino, Integer idPsicologo) {
        if (bambino.getIdIstituto().equals(1)) {
            bambino.setClasse("");
            bambino.setSezione("");
        }
        if (bambino.getId() == null) {
            bambino.setTestFinito(false);
            bambino.setDataTest(null);
        }
        bambinoRepository.save(bambino);

        RelPsicologoBambino relPsicologoBambino = new RelPsicologoBambino();
        relPsicologoBambino.setIdBambino(bambino.getId());
        relPsicologoBambino.setIdPsicologo(idPsicologo);
        relPsicologoBambinoRepository.save(relPsicologoBambino);
    }

    @Transactional(rollbackFor = Exception.class)
    public void deleteChild(Integer idBambino) {
        relBambinoSchedaRepository.deleteByIdBambino(idBambino);
        relPsicologoBambinoRepository.deleteByIdBambino(idBambino);
        bambinoRepository.deleteById(idBambino);
    }

}