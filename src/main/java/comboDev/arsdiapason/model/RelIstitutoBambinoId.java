package comboDev.arsdiapason.model;

import lombok.Data;

import java.io.Serializable;

@Data
public class RelIstitutoBambinoId implements Serializable {
    private Integer idIstituto;
    private Integer idBambino;
}