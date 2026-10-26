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

        // 1. Recupera il bambino
        Bambino bambino = bambinoRepository.findById(idBambino)
                .orElseThrow(() -> new RuntimeException("Bambino non trovato con id: " + idBambino));

        // 2. Recupera la relazione con l'istituto associata al bambino
        List<RelIstitutoBambino> relIstitutoBambinoList = relIstitutoBambinoRepository.findAll().stream()
                .filter(rib -> rib.getIdBambino().equals(bambino.getId()))
                .toList();

        if (!relIstitutoBambinoList.isEmpty()) {
            RelIstitutoBambino relIB = relIstitutoBambinoList.getFirst();
            BambinoDto bambinoDto = BambinoDto.fromEntity(bambino, relIB.getIdIstituto(), relIB.getClasse(), relIB.getSezione());
            resultChildTest.setBambino(bambinoDto);
        }

        // 3. Recupera le schede/prove collegate al bambino
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