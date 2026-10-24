package comboDev.arsdiapason.model;

import lombok.Data;

import java.io.Serializable;

@Data
public class RelPsicologoIstitutoId implements Serializable {
    private Integer idPsicologo;
    private Integer idIstituto;
}