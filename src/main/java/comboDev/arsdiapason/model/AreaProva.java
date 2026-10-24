package comboDev.arsdiapason.model;

import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;

@Entity
@Table(name = "area_prova")
@IdClass(AreaProvaId.class)
@Data
public class AreaProva {

    @Id
    @Column(name = "prova", nullable = false)
    private Byte prova;

    @Id
    @Column(name = "anni", nullable = false)
    private Byte anni;

    @Column(name = "testo", nullable = false, length = 85)
    private String testo;

    @CreationTimestamp
    @Column(name = "data_ins", nullable = false, updatable = false)
    private LocalDateTime dataIns;

    @UpdateTimestamp
    @Column(name = "data_mod", nullable = false)
    private LocalDateTime dataMod;
}