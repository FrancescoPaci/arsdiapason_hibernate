package comboDev.arsdiapason.model;

import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;

@Entity
@Table(name = "rel_psicologo_istituto")
@IdClass(RelPsicologoIstitutoId.class)
@Data
public class RelPsicologoIstituto {

    @Id
    @Column(name = "id_psicologo", nullable = false)
    private Integer idPsicologo;

    @Id
    @Column(name = "id_istituto", nullable = false)
    private Integer idIstituto;

    @CreationTimestamp
    @Column(name = "data_ins", nullable = false, updatable = false)
    private LocalDateTime dataIns;

    @UpdateTimestamp
    @Column(name = "data_mod", nullable = false)
    private LocalDateTime dataMod;
}