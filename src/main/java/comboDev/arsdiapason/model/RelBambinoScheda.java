package comboDev.arsdiapason.model;

import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;

@Entity
@Table(name = "rel_bambino_scheda")
@IdClass(RelBambinoSchedaId.class)
@Data
public class RelBambinoScheda {

    @Id
    @Column(name = "id_bambino")
    private Integer idBambino;

    @Id
    @Column(name = "id_prova_scheda")
    private Byte idProvaScheda;

    @CreationTimestamp
    @Column(name = "data_ins", nullable = false, updatable = false)
    private LocalDateTime dataIns;

    @UpdateTimestamp
    @Column(name = "data_mod", nullable = false)
    private LocalDateTime dataMod;
}