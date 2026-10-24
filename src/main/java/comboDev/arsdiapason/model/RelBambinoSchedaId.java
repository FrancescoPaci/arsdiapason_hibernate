package comboDev.arsdiapason.model;

import lombok.Data;

import java.io.Serializable;

@Data
public class RelBambinoSchedaId implements Serializable {
    private Integer idBambino;
    private Byte idProvaScheda;
}