package comboDev.arsdiapason.dto;

import comboDev.arsdiapason.model.Bambino;
import comboDev.arsdiapason.model.ProvaScheda;
import comboDev.arsdiapason.model.RelBambinoScheda;
import comboDev.arsdiapason.model.Scheda;
import lombok.Data;

import java.util.List;

@Data
public class DatiSchede {
    Bambino bambino;
    List<Scheda> schede;
    List<ProvaScheda> proveSchede;
    List<RelBambinoScheda> relBambinoSchede;
}
