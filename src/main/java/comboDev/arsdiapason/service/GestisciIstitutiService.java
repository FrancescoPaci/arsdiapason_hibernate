package comboDev.arsdiapason.service;

import comboDev.arsdiapason.model.Istituto;
import comboDev.arsdiapason.repository.IstitutoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

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
        return istitutoRepository.save(istituto);
    }

    @Transactional(rollbackFor = Exception.class)
    public Istituto saveModIstituto(Istituto istituto) {
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