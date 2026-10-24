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

        // 1. Recupera tutte le relazioni psicologo-bambino per quell'ID
        // (Nota: puoi aggiungere un metodo custom nel repository se preferisci, oppure filtrare)
        List<RelPsicologoBambino> relations = relPsicologoBambinoRepository.findAll().stream()
                .filter(r -> r.getIdPsicologo().equals(idPsicologo))
                .toList();

        List<BambinoDto> bambini = new ArrayList<>();

        for (RelPsicologoBambino relation : relations) {
            // 2. Recupera il bambino tramite JPA
            Optional<Bambino> bambinoOpt = bambinoRepository.findById(relation.getIdBambino());

            if (bambinoOpt.isPresent()) {
                Bambino bambino = bambinoOpt.get();

                // 3. Recupera la relazione con l'istituto per il bambino corrente
                List<RelIstitutoBambino> relIstitutoBambinoList = relIstitutoBambinoRepository.findAll().stream()
                        .filter(rib -> rib.getIdBambino().equals(bambino.getId()))
                        .toList();

                if (!relIstitutoBambinoList.isEmpty()) {
                    RelIstitutoBambino relIB = relIstitutoBambinoList.get(0);

                    // 4. Mappa nel DTO esattamente come facevi prima
                    BambinoDto bambinoDto = BambinoDto.fromEntity(
                            bambino,
                            relIB.getIdIstituto(),
                            relIB.getClasse(),
                            relIB.getSezione()
                    );
                    bambini.add(bambinoDto);
                }
            }
        }
        return bambini;
    }
}