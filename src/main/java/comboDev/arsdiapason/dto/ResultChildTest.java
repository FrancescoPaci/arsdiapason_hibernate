package comboDev.arsdiapason.dto;

import comboDev.arsdiapason.model.AreaProva;
import comboDev.arsdiapason.model.Bambino;
import comboDev.arsdiapason.model.ProvaScheda;
import lombok.Data;

import java.util.List;

@Data
public class ResultChildTest {
    Bambino bambino;
    List<ProvaScheda> proveSchede;
    List<AreaProva> areaProva;
}
