package comboDev.arsdiapason.service;

import comboDev.arsdiapason.model.Istituto;
import comboDev.arsdiapason.repository.IstitutoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class GestisciIstitutiService {

    @Autowired
    private IstitutoRepository istitutoRepository;

    @Transactional(readOnly = true)
    public List<Istituto> getListaIstituti() {
        return istitutoRepository.findAll();
    }

    @Transactional(rollbackFor = Exception.class)
    public Istituto inserisciIstituto(Istituto istituto) {
        istituto.setSezioni(formatSezioni(istituto.getSezioni()));
        return istitutoRepository.save(istituto);
    }

    @Transactional(rollbackFor = Exception.class)
    public Istituto saveModIstituto(Istituto istituto) {
        istituto.setSezioni(formatSezioni(istituto.getSezioni()));
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

    public static String formatSezioni(String sezioni) {
        if (sezioni == null || sezioni.trim().isEmpty()) {
            return sezioni;
        }
        return Arrays.stream(sezioni.split(","))
                .map(String::trim)
                .filter(s -> s.matches("^[a-zA-Z]$"))
                .map(String::toUpperCase)
                .sorted()
                .collect(Collectors.joining(","));
    }

}