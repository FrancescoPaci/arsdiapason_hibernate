package comboDev.arsdiapason.model;

import lombok.Data;

import java.io.Serializable;

@Data
public class AreaProvaId implements Serializable {
    private Byte prova;
    private Byte anni;
}