package comboDev.arsdiapason.service;

import comboDev.arsdiapason.dto.BambinoDto;
import comboDev.arsdiapason.dto.ResultChildTest;
import comboDev.arsdiapason.mybatis.mapper.*;
import comboDev.arsdiapason.mybatis.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
public class PaginaGraficiService {

    @Autowired
    private BambinoMapper bambinoMapper;
    @Autowired
    private RelPsicologoBambinoMapper relPsicologoBambinoMapper;
    @Autowired
    private RelIstitutoBambinoMapper relIstitutoBambinoMapper;
    @Autowired
    private RelBambinoSchedaMapper relBambinoSchedaMapper;
    @Autowired
    private ProvaSchedaMapper provaSchedaMapper;

    @Transactional(readOnly = true)
    public List<ResultChildTest> getAllTests(Integer idPsicologo) {
        RelPsicologoBambinoExample relPsicologoBambinoExample = new RelPsicologoBambinoExample();
        relPsicologoBambinoExample.createCriteria().andIdPsicologoEqualTo(idPsicologo);
        List<RelPsicologoBambino> relations = relPsicologoBambinoMapper.selectByExample(relPsicologoBambinoExample);

        List<ResultChildTest> resultChildTests = new ArrayList<>();

        for(RelPsicologoBambino relation: relations) {

            ResultChildTest resultChildTest = new ResultChildTest();

            BambinoExample bambinoExample = new BambinoExample();
            bambinoExample.createCriteria().andIdEqualTo(relation.getIdBambino()).andTestFinitoEqualTo(true);
            List<Bambino> bambini = bambinoMapper.selectByExample(bambinoExample);

            if (bambini.size() == 1) {
                Bambino bambino = bambini.get(0);

                RelIstitutoBambinoExample relIstitutoBambinoExample = new RelIstitutoBambinoExample();
                relIstitutoBambinoExample.createCriteria().andIdBambinoEqualTo(bambino.getId());
                List<RelIstitutoBambino> relIstitutoBambino = relIstitutoBambinoMapper.selectByExample(relIstitutoBambinoExample);
                RelIstitutoBambino relIB = relIstitutoBambino.get(0);
                BambinoDto bambinoDto = BambinoDto.fromEntity(bambino, relIB.getIdIstituto(), relIB.getClasse(), relIB.getSezione());

                RelBambinoSchedaExample relBambinoSchedaExample = new RelBambinoSchedaExample();
                relBambinoSchedaExample.createCriteria().andIdBambinoEqualTo(bambino.getId());
                List<RelBambinoScheda> relBambinoSchedaList = relBambinoSchedaMapper.selectByExample(relBambinoSchedaExample);

                List<ProvaScheda> provaSchedas = new ArrayList<>();
                for (RelBambinoScheda relBambinoScheda : relBambinoSchedaList) {
                    ProvaScheda provaScheda = provaSchedaMapper.selectByPrimaryKey(relBambinoScheda.getIdProvaScheda());
                    if (provaScheda != null) {
                        provaSchedas.add(provaSchedaMapper.selectByPrimaryKey(relBambinoScheda.getIdProvaScheda()));
                    }
                }
                if (!provaSchedas.isEmpty()) {
                    resultChildTest.setProveSchede(provaSchedas);
                    resultChildTest.setBambino(bambinoDto);
                    resultChildTests.add(resultChildTest);
                }
            }
        }
        return resultChildTests;
    }

}
