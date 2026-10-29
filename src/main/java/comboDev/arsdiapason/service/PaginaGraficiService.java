package comboDev.arsdiapason.service;

import comboDev.arsdiapason.dto.ResultChildTest;
import comboDev.arsdiapason.model.Bambino;
import comboDev.arsdiapason.model.ProvaScheda;
import comboDev.arsdiapason.model.RelBambinoScheda;
import comboDev.arsdiapason.model.RelPsicologoBambino;
import comboDev.arsdiapason.repository.BambinoRepository;
import comboDev.arsdiapason.repository.ProvaSchedaRepository;
import comboDev.arsdiapason.repository.RelBambinoSchedaRepository;
import comboDev.arsdiapason.repository.RelPsicologoBambinoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class PaginaGraficiService {

    @Autowired
    private BambinoRepository bambinoRepository;
    @Autowired
    private RelPsicologoBambinoRepository relPsicologoBambinoRepository;
    @Autowired
    private RelBambinoSchedaRepository relBambinoSchedaRepository;
    @Autowired
    private ProvaSchedaRepository provaSchedaRepository;

    @Transactional(readOnly = true)
    public List<ResultChildTest> getAllTests(Integer idPsicologo) {

        List<ResultChildTest> resultChildTests = new ArrayList<>();

        List<RelPsicologoBambino> relPB = relPsicologoBambinoRepository.findByidPsicologo(idPsicologo);

        for (RelPsicologoBambino relation : relPB) {

            ResultChildTest resultChildTest = new ResultChildTest();

            Optional<Bambino> bambinoOpt = bambinoRepository.findByIdAndTestFinitoTrue(relation.getIdBambino());

            if (bambinoOpt.isPresent()) {
                Bambino bambino = bambinoOpt.get();

                List<RelBambinoScheda> relBambinoSchedaList = relBambinoSchedaRepository.findByIdBambino(bambino.getId());

                List<ProvaScheda> provaSchedaList = new ArrayList<>();
                for (RelBambinoScheda relBambinoScheda : relBambinoSchedaList) {
                    Optional<ProvaScheda> provaSchedaOpt = provaSchedaRepository.findById(relBambinoScheda.getIdProvaScheda());
                    provaSchedaOpt.ifPresent(provaSchedaList::add);
                }

                if (!provaSchedaList.isEmpty()) {
                    resultChildTest.setProveSchede(provaSchedaList);
                    resultChildTest.setBambino(bambino);
                    resultChildTests.add(resultChildTest);
                }
            }
        }
        return resultChildTests;
    }

}