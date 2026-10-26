package comboDev.arsdiapason.service;

import comboDev.arsdiapason.dto.BambinoDto;
import comboDev.arsdiapason.dto.ResultChildTest;
import comboDev.arsdiapason.model.*;
import comboDev.arsdiapason.repository.*;
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
    private RelIstitutoBambinoRepository relIstitutoBambinoRepository;
    @Autowired
    private RelBambinoSchedaRepository relBambinoSchedaRepository;
    @Autowired
    private ProvaSchedaRepository provaSchedaRepository;

    @Transactional(readOnly = true)
    public List<ResultChildTest> getAllTests(Integer idPsicologo) {

        // 1. Recupera tutte le relazioni psicologo-bambino filtrando per idPsicologo
        List<RelPsicologoBambino> relations = relPsicologoBambinoRepository.findAll().stream()
                .filter(r -> r.getIdPsicologo().equals(idPsicologo))
                .toList();

        List<ResultChildTest> resultChildTests = new ArrayList<>();

        for (RelPsicologoBambino relation : relations) {

            ResultChildTest resultChildTest = new ResultChildTest();

            // 2. Cerca il bambino solo se ha testFinito == true
            Optional<Bambino> bambinoOpt = bambinoRepository.findByIdAndTestFinitoTrue(relation.getIdBambino());

            if (bambinoOpt.isPresent()) {
                Bambino bambino = bambinoOpt.get();

                // 3. Recupera la relazione con l'istituto
                List<RelIstitutoBambino> relIstitutoBambinoList = relIstitutoBambinoRepository.findAll().stream()
                        .filter(rib -> rib.getIdBambino().equals(bambino.getId()))
                        .toList();

                if (!relIstitutoBambinoList.isEmpty()) {
                    RelIstitutoBambino relIB = relIstitutoBambinoList.getFirst();
                    BambinoDto bambinoDto = BambinoDto.fromEntity(bambino, relIB.getIdIstituto(), relIB.getClasse(), relIB.getSezione());

                    // 4. Recupera le relazioni delle schede del bambino
                    List<RelBambinoScheda> relBambinoSchedaList = relBambinoSchedaRepository.findAll().stream()
                            .filter(rbs -> rbs.getIdBambino().equals(bambino.getId()))
                            .toList();

                    List<ProvaScheda> provaSchedas = new ArrayList<>();
                    for (RelBambinoScheda relBambinoScheda : relBambinoSchedaList) {
                        Optional<ProvaScheda> provaSchedaOpt = provaSchedaRepository.findById(relBambinoScheda.getIdProvaScheda());
                        provaSchedaOpt.ifPresent(provaSchedas::add);
                    }

                    if (!provaSchedas.isEmpty()) {
                        resultChildTest.setProveSchede(provaSchedas);
                        resultChildTest.setBambino(bambinoDto);
                        resultChildTests.add(resultChildTest);
                    }
                }
            }
        }
        return resultChildTests;
    }
}