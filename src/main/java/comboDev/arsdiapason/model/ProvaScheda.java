package comboDev.arsdiapason.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;

@Entity
@Table(name = "prova_scheda")
@Data
public class ProvaScheda {

    @Id
    @Column(name = "id")
    private Byte id;

    @Column(name = "numero_scheda", nullable = false)
    private Byte numeroScheda;

    @Column(name = "tipo", length = 10)
    private String tipo;

    @Column(name = "testo", nullable = false, length = 70)
    private String testo;

    @Column(name = "anni", nullable = false)
    private Byte anni;

    @Column(name = "prova")
    private Byte prova;

    @CreationTimestamp
    @Column(name = "data_ins", nullable = false, updatable = false)
    private LocalDateTime dataIns;

    @UpdateTimestamp
    @Column(name = "data_mod", nullable = false)
    private LocalDateTime dataMod;
}