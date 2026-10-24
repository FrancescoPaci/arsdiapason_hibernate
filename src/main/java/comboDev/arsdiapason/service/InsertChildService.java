package comboDev.arsdiapason.service;

import comboDev.arsdiapason.dto.BambinoDto;
import comboDev.arsdiapason.dto.IstitutoDettagli;
import comboDev.arsdiapason.mybatis.customMapper.CustomMapper;
import comboDev.arsdiapason.mybatis.mapper.BambinoMapper;
import comboDev.arsdiapason.mybatis.mapper.RelIstitutoBambinoMapper;
import comboDev.arsdiapason.mybatis.mapper.RelPsicologoBambinoMapper;
import comboDev.arsdiapason.mybatis.model.RelIstitutoBambino;
import comboDev.arsdiapason.mybatis.model.RelPsicologoBambino;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class InsertChildService {

    @Autowired
    private BambinoMapper bambinoMapper;
    @Autowired
    private RelPsicologoBambinoMapper relPsicologoBambinoMapper;
    @Autowired
    private RelIstitutoBambinoMapper relIstitutoBambinoMapper;
    @Autowired
    private CustomMapper customMapper;

    @Transactional(readOnly = true)
    public IstitutoDettagli findInstitutoDetails(Integer idIstituto) {
        List<Integer> classi = customMapper.findClassi(idIstituto);
        List<String> sezioni = customMapper.findSezioni(idIstituto);
        IstitutoDettagli istitutoDettagli = new IstitutoDettagli();
        istitutoDettagli.setClassi(classi);
        istitutoDettagli.setSezioni(sezioni);
        return istitutoDettagli;
    }

    @Transactional(rollbackFor = Exception.class)
    public void insertChild(BambinoDto bambino, Integer idPsicologo) {

        bambinoMapper.insertSelective(bambino);

        RelPsicologoBambino relPsicologoBambino = new RelPsicologoBambino();
        relPsicologoBambino.setIdBambino(bambino.getId());
        relPsicologoBambino.setIdPsicologo(idPsicologo);
        relPsicologoBambinoMapper.insert(relPsicologoBambino);

        RelIstitutoBambino relIstitutoBambino = new RelIstitutoBambino();
        relIstitutoBambino.setIdBambino(bambino.getId());
        relIstitutoBambino.setIdIstituto(bambino.getIdIstituto());
        relIstitutoBambino.setClasse(bambino.getClasse());
        relIstitutoBambino.setSezione(bambino.getSezione().toUpperCase());
        relIstitutoBambinoMapper.insert(relIstitutoBambino);
    }

}
