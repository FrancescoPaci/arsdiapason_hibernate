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

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ChildListService {

    @Autowired
    private BambinoRepository bambinoRepository;

    @Autowired
    private RelPsicologoBambinoRepository relPsicologoBambinoRepository;

    @Autowired
    private RelIstitutoBambinoRepository relIstitutoBambinoRepository;

    @Transactional(readOnly = true)
    public List<BambinoDto> childsList(Integer idPsicologo) {

        List<BambinoDto> childs = new ArrayList<>();

        List<RelPsicologoBambino> relPsicologoBambino = relPsicologoBambinoRepository.findByidPsicologo(idPsicologo);

        for (RelPsicologoBambino relPB : relPsicologoBambino) {

            List<RelIstitutoBambino> relIstitutoBambino = relIstitutoBambinoRepository.findByidBambino(relPB.getIdBambino());

            for (RelIstitutoBambino relIB : relIstitutoBambino) {

                Optional<Bambino> bambinoOpt = bambinoRepository.findById(relIB.getIdBambino());

                if (bambinoOpt.isPresent()) {
                    Bambino bambino = bambinoOpt.get();

                    BambinoDto bambinoDto = BambinoDto.fromEntity(
                            bambino,
                            relIB.getId(),
                            relIB.getIdIstituto(),
                            relIB.getClasse(),
                            relIB.getSezione()
                    );
                    childs.add(bambinoDto);
                }
            }
        }
        return childs;
    }

}