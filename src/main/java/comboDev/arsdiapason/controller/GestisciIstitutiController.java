package comboDev.arsdiapason.controller;

import comboDev.arsdiapason.model.Istituto;
import comboDev.arsdiapason.model.enums.ClasseEnum;
import comboDev.arsdiapason.service.GestisciIstitutiService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class GestisciIstitutiController implements BasicController {

    @Autowired
    private GestisciIstitutiService gestisciIstitutiService;

    @PostMapping("/getListaIstituti")
    public List<Istituto> getListaIstituti() {
        return gestisciIstitutiService.getListaIstituti();
    }

    @GetMapping("/getClassi")
    public List<ClasseEnum> getClassi() {
        return List.of(ClasseEnum.values());
    }

    @PostMapping("/inserisciIstituto")
    public Istituto inserisciIstituto(@RequestBody Istituto istituto) {
        return gestisciIstitutiService.inserisciIstituto(istituto);
    }

    @PostMapping("/saveModIstituto")
    public Istituto saveModIstituto(@RequestBody Istituto istituto) {
        return gestisciIstitutiService.saveModIstituto(istituto);
    }

    @PostMapping("/eliminaIstituto")
    public void eliminaIstituto(@RequestParam Integer idIstituto) throws Exception {
        gestisciIstitutiService.eliminaIstituto(idIstituto);
    }

}