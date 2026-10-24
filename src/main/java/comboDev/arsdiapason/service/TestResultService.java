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
public class TestResultService {

    @Autowired
    private BambinoMapper bambinoMapper;
    @Autowired
    private ProvaSchedaMapper provaSchedaMapper;
    @Autowired
    private RelBambinoSchedaMapper relBambinoSchedaMapper;
    @Autowired
    private RelIstitutoBambinoMapper relIstitutoBambinoMapper;
    @Autowired
    private AreaProvaMapper areaProvaMapper;

    @Transactional(readOnly = true)
    public ResultChildTest getResultChildTest(Integer idBambino) {
        ResultChildTest resultChildTest = new ResultChildTest();
        Bambino bambino = bambinoMapper.selectByPrimaryKey(idBambino);

        RelIstitutoBambinoExample relIstitutoBambinoExample = new RelIstitutoBambinoExample();
        relIstitutoBambinoExample.createCriteria().andIdBambinoEqualTo(bambino.getId());
        List<RelIstitutoBambino> relIstitutoBambino = relIstitutoBambinoMapper.selectByExample(relIstitutoBambinoExample);
        RelIstitutoBambino relIB = relIstitutoBambino.get(0);
        BambinoDto bambinoDto = BambinoDto.fromEntity(bambino, relIB.getIdIstituto(), relIB.getClasse(), relIB.getSezione());
        resultChildTest.setBambino(bambinoDto);

        RelBambinoSchedaExample relBambinoSchedaExample = new RelBambinoSchedaExample();
        relBambinoSchedaExample.createCriteria().andIdBambinoEqualTo(idBambino);
        List<RelBambinoScheda> relBambinoSchedas = relBambinoSchedaMapper.selectByExample(relBambinoSchedaExample);
        List<ProvaScheda> provaSchedas = new ArrayList<>();
        for (RelBambinoScheda relBambinoScheda : relBambinoSchedas) {
            provaSchedas.add(provaSchedaMapper.selectByPrimaryKey(relBambinoScheda.getIdProvaScheda()));
        }
        resultChildTest.setProveSchede(provaSchedas);
        resultChildTest.setAreaProva(areaProvaMapper.selectByExample(null));
        return resultChildTest;
    }

}
