package comboDev.arsdiapason.service;

import comboDev.arsdiapason.dto.BambinoDto;
import comboDev.arsdiapason.mybatis.mapper.BambinoMapper;
import comboDev.arsdiapason.mybatis.mapper.RelIstitutoBambinoMapper;
import comboDev.arsdiapason.mybatis.mapper.RelPsicologoBambinoMapper;
import comboDev.arsdiapason.mybatis.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
public class ChildListService {

    @Autowired
    private BambinoMapper bambinoMapper;
    @Autowired
    private RelPsicologoBambinoMapper relPsicologoBambinoMapper;
    @Autowired
    private RelIstitutoBambinoMapper relIstitutoBambinoMapper;

    @Transactional(readOnly = true)
    public List<BambinoDto> childsList(Integer idPsicologo) {

        RelPsicologoBambinoExample relPsicologoBambinoExample = new RelPsicologoBambinoExample();
        relPsicologoBambinoExample.createCriteria().andIdPsicologoEqualTo(idPsicologo);
        List<RelPsicologoBambino> relations = relPsicologoBambinoMapper.selectByExample(relPsicologoBambinoExample);

        List<BambinoDto> bambini = new ArrayList<>();
        for (RelPsicologoBambino relation : relations) {
            Bambino bambino = bambinoMapper.selectByPrimaryKey(relation.getIdBambino());
            if (bambino != null) {
                RelIstitutoBambinoExample relIstitutoBambinoExample = new RelIstitutoBambinoExample();
                relIstitutoBambinoExample.createCriteria().andIdBambinoEqualTo(bambino.getId());
                List<RelIstitutoBambino> relIstitutoBambino = relIstitutoBambinoMapper.selectByExample(relIstitutoBambinoExample);
                RelIstitutoBambino relIB = relIstitutoBambino.get(0);
                BambinoDto bambinoDto = BambinoDto.fromEntity(bambino, relIB.getIdIstituto(), relIB.getClasse(), relIB.getSezione());
                bambini.add(bambinoDto);
            }
        }
        return bambini;
    }

}
