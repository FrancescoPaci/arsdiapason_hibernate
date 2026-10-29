package comboDev.arsdiapason.service;

import comboDev.arsdiapason.dto.BambinoDto;
import comboDev.arsdiapason.model.Bambino;
import comboDev.arsdiapason.model.RelIstitutoBambino;
import comboDev.arsdiapason.model.RelPsicologoBambino;
import comboDev.arsdiapason.repository.BambinoRepository;
import comboDev.arsdiapason.repository.RelIstitutoBambinoRepository;
import comboDev.arsdiapason.repository.RelPsicologoBambinoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static comboDev.arsdiapason.dto.BambinoDto.toEntity;

@Service
public class InsertChildService {

    @Autowired
    private BambinoRepository bambinoRepository;

    @Autowired
    private RelPsicologoBambinoRepository relPsicologoBambinoRepository;

    @Autowired
    private RelIstitutoBambinoRepository relIstitutoBambinoRepository;

    @Transactional(rollbackFor = Exception.class)
    public void insertChild(BambinoDto bambinoDto, Integer idPsicologo) {

        Bambino bambino = bambinoRepository.save(toEntity(bambinoDto));

        RelPsicologoBambino relPsicologoBambino = new RelPsicologoBambino();
        relPsicologoBambino.setIdBambino(bambino.getId());
        relPsicologoBambino.setIdPsicologo(idPsicologo);
        relPsicologoBambinoRepository.save(relPsicologoBambino);

        RelIstitutoBambino relIstitutoBambino = new RelIstitutoBambino();
        relIstitutoBambino.setIdBambino(bambino.getId());
        relIstitutoBambino.setIdIstituto(bambinoDto.getIdIstituto());

        if (bambinoDto.getIdIstituto().equals(1)) {
            relIstitutoBambino.setClasse(0);
            relIstitutoBambino.setSezione("");
        } else {
            relIstitutoBambino.setClasse(bambinoDto.getClasse());
            relIstitutoBambino.setSezione(bambinoDto.getSezione());
        }
        if (bambinoDto.getIdRelIstitutoBambino() != null) {
            relIstitutoBambino.setId(bambinoDto.getIdRelIstitutoBambino());
        }
        relIstitutoBambinoRepository.save(relIstitutoBambino);
    }

    @Transactional(rollbackFor = Exception.class)
    public void deleteChild(Integer idBambino) {
        relPsicologoBambinoRepository.deleteByIdBambino(idBambino);
        relIstitutoBambinoRepository.deleteByIdBambino(idBambino);
        bambinoRepository.deleteById(idBambino);
    }

}