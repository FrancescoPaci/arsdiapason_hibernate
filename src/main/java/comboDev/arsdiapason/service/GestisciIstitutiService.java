package comboDev.arsdiapason.service;

import comboDev.arsdiapason.model.Istituto;
import comboDev.arsdiapason.model.RelPsicologoIstituto;
import comboDev.arsdiapason.repository.IstitutoRepository;
import comboDev.arsdiapason.repository.RelPsicologoIstitutoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.List;

@Service
public class GestisciIstitutiService {

    @Autowired
    private IstitutoRepository istitutoRepository;

    @Autowired
    private RelPsicologoIstitutoRepository relPsicologoIstitutoRepository;

    @Transactional(readOnly = true)
    public List<Istituto> getListaIstituti(Integer idPsicologo) {
        // 1. Recupera tutte le associazioni per lo psicologo dato
        List<RelPsicologoIstituto> relazioni = relPsicologoIstitutoRepository.findByIdPsicologo(idPsicologo);

        // 2. Estrai la lista degli ID degli istituti
        List<Integer> idsIstituti = relazioni.stream()
                .map(RelPsicologoIstituto::getIdIstituto)
                .toList(); // oppure .collect(Collectors.toList()) se usi Java precedenti

        // 3. Se non ci sono istituti associati, restituisci una lista vuota
        if (idsIstituti.isEmpty()) {
            return Collections.emptyList();
        }

        // 4. Recupera e restituisce gli istituti tramite il loro ID
        return istitutoRepository.findAllById(idsIstituti);
    }

    @Transactional(rollbackFor = Exception.class)
    public Istituto inserisciIstituto(Istituto istituto, Integer idPsicologo) {
        // 1. Salviamo l'istituto sul database (restituisce l'entità con l'id popolato)
        Istituto istitutoSalvato = istitutoRepository.save(istituto);

        // 2. Creiamo l'associazione nella tabella di join rel_psicologo_istituto
        RelPsicologoIstituto relazione = new RelPsicologoIstituto();
        relazione.setIdPsicologo(idPsicologo);
        relazione.setIdIstituto(istitutoSalvato.getId());

        relPsicologoIstitutoRepository.save(relazione);

        // 3. Restituiamo l'istituto salvato
        return istitutoSalvato;
    }

    @Transactional(rollbackFor = Exception.class)
    public Istituto saveModIstituto(Istituto istituto) {
        // Con JPA, save() aggiorna l'entità esistente se l'ID è presente nel database
        return istitutoRepository.save(istituto);
    }

    @Transactional(rollbackFor = Exception.class)
    public void eliminaIstituto(Integer idIstituto) throws Exception {
        try {
            istitutoRepository.deleteById(idIstituto);
        } catch (DataIntegrityViolationException e) {
            throw new Exception("", new Throwable("Non è possibile eliminare l'istituto in quanto collegato a dei bambini"));
        }
    }
}