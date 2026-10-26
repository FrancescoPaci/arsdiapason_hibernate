package comboDev.arsdiapason.controller;

import comboDev.arsdiapason.dto.BambinoDto;
import comboDev.arsdiapason.dto.IstitutoDettagli;
import comboDev.arsdiapason.service.InsertChildService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class InsertChildController implements BasicController {

    @Autowired
    private InsertChildService insertChildService;

    @PostMapping("/insertChild")
    public void insertChild(Authentication authentication, @RequestBody BambinoDto bambino) {
        insertChildService.insertChild(bambino, (Integer) authentication.getDetails());
    }

    @PostMapping("/deleteChild")
    public void deleteChild(@RequestParam Integer idBambino) {
        insertChildService.deleteChild(idBambino);
    }

}