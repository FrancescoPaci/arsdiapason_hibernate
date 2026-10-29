package comboDev.arsdiapason.service;

import comboDev.arsdiapason.dto.BambinoDto;
import comboDev.arsdiapason.dto.ResultChildTest;
import comboDev.arsdiapason.model.Bambino;
import comboDev.arsdiapason.model.ProvaScheda;
import comboDev.arsdiapason.model.RelBambinoScheda;
import comboDev.arsdiapason.model.RelIstitutoBambino;
import comboDev.arsdiapason.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class TestResultService {

    @Autowired
    private BambinoRepository bambinoRepository;
    @Autowired
    private ProvaSchedaRepository provaSchedaRepository;
    @Autowired
    private RelBambinoSchedaRepository relBambinoSchedaRepository;
    @Autowired
    private RelIstitutoBambinoRepository relIstitutoBambinoRepository;
    @Autowired
    private AreaProvaRepository areaProvaRepository;

    @Transactional(readOnly = true)
    public ResultChildTest getResultChildTest(Integer idBambino) {
        ResultChildTest resultChildTest = new ResultChildTest();

        Bambino bambino = bambinoRepository.findById(idBambino)
                .orElseThrow(() -> new RuntimeException("Bambino non trovato con id: " + idBambino));

        List<RelIstitutoBambino> relIstitutoBambinoList = relIstitutoBambinoRepository.findByidBambino(bambino.getId());

        for (RelIstitutoBambino relIB : relIstitutoBambinoList) {
            BambinoDto bambinoDto = BambinoDto.fromEntity(bambino, relIB.getId(), relIB.getIdIstituto(), relIB.getClasse(), relIB.getSezione());
            resultChildTest.setBambino(bambinoDto);
        }

        List<RelBambinoScheda> relBambinoSchedas = relBambinoSchedaRepository.findByIdBambino(idBambino);
        List<ProvaScheda> provaSchedas = new ArrayList<>();

        for (RelBambinoScheda relBambinoScheda : relBambinoSchedas) {
            Optional<ProvaScheda> provaSchedaOpt = provaSchedaRepository.findById(relBambinoScheda.getIdProvaScheda());
            provaSchedaOpt.ifPresent(provaSchedas::add);
        }

        resultChildTest.setProveSchede(provaSchedas);

        // 4. Recupera tutte le aree di prova (sostituisce selectByExample(null))
        resultChildTest.setAreaProva(areaProvaRepository.findAll());

        return resultChildTest;
    }
}