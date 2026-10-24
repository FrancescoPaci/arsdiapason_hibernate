package comboDev.arsdiapason.service;

import comboDev.arsdiapason.dto.BambinoDto;
import comboDev.arsdiapason.dto.IstitutoDettagli;
import comboDev.arsdiapason.model.Bambino;
import comboDev.arsdiapason.model.RelIstitutoBambino;
import comboDev.arsdiapason.model.RelPsicologoBambino;
import comboDev.arsdiapason.repository.BambinoRepository;
import comboDev.arsdiapason.repository.RelIstitutoBambinoRepository;
import comboDev.arsdiapason.repository.RelPsicologoBambinoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class InsertChildService {

    @Autowired
    private BambinoRepository bambinoRepository;

    @Autowired
    private RelPsicologoBambinoRepository relPsicologoBambinoRepository;

    @Autowired
    private RelIstitutoBambinoRepository relIstitutoBambinoRepository;

    @Transactional(readOnly = true)
    public IstitutoDettagli findInstitutoDetails(Integer idIstituto) {
        // Sfrutta i repository JPA con query dedicate
        List<Integer> classi = relIstitutoBambinoRepository.findDistinctClassiByIdIstituto(idIstituto);
        List<String> sezioni = relIstitutoBambinoRepository.findDistinctSezioniByIdIstituto(idIstituto);

        IstitutoDettagli istitutoDettagli = new IstitutoDettagli();
        istitutoDettagli.setClassi(classi);
        istitutoDettagli.setSezioni(sezioni);
        return istitutoDettagli;
    }

    @Transactional(rollbackFor = Exception.class)
    public void insertChild(BambinoDto bambinoDto, Integer idPsicologo) {

        // 1. Mappa il DTO nell'Entity Bambino (presumendo che il tuo BambinoDto abbia un metodo toEntity o che tu lo mappi manualmente)
        Bambino bambino = new Bambino();
        bambino.setNome(bambinoDto.getNome());
        bambino.setCognome(bambinoDto.getCognome());
        bambino.setSesso(bambinoDto.getSesso());
        bambino.setDataNascita(bambinoDto.getDataNascita());
        bambino.setComuneNascita(bambinoDto.getComuneNascita());
        bambino.setComuneResidenza(bambinoDto.getComuneResidenza());
        bambino.setIndirizzoResidenza(bambinoDto.getIndirizzoResidenza());
        bambino.setNumeroFratelli(bambinoDto.getNumeroFratelli());
        bambino.setNumeroSorelle(bambinoDto.getNumeroSorelle());
        bambino.setOrdineGenitura(bambinoDto.getOrdineGenitura());
        bambino.setLavoroPadre(bambinoDto.getLavoroPadre());
        bambino.setLavoroMadre(bambinoDto.getLavoroMadre());
        bambino.setTitoloStudioPadre(bambinoDto.getTitoloStudioPadre());
        bambino.setTitoloStudioMadre(bambinoDto.getTitoloStudioMadre());
        bambino.setFiglioAdottivo(bambinoDto.getFiglioAdottivo());
        bambino.setNote(bambinoDto.getNote());

        // Salva il bambino: JPA popolerà automaticamente l'ID autoincrementante sull'oggetto salvato
        bambino = bambinoRepository.save(bambino);

        // 2. Salva la relazione con lo psicologo
        RelPsicologoBambino relPsicologoBambino = new RelPsicologoBambino();
        relPsicologoBambino.setIdBambino(bambino.getId());
        relPsicologoBambino.setIdPsicologo(idPsicologo);
        relPsicologoBambinoRepository.save(relPsicologoBambino);

        // 3. Salva la relazione con l'istituto
        RelIstitutoBambino relIstitutoBambino = new RelIstitutoBambino();
        relIstitutoBambino.setIdBambino(bambino.getId());
        relIstitutoBambino.setIdIstituto(bambinoDto.getIdIstituto());
        relIstitutoBambino.setClasse(bambinoDto.getClasse());
        relIstitutoBambino.setSezione(bambinoDto.getSezione().toUpperCase());
        relIstitutoBambinoRepository.save(relIstitutoBambino);
    }
}