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
@Table(name = "scheda")
@Data
public class Scheda {

    @Id
    @Column(name = "numero")
    private Byte numero;

    @Column(name = "titolo", nullable = false, length = 30)
    private String titolo;

    @Column(name = "obbiettivo", nullable = false, length = 80)
    private String obbiettivo;

    @Column(name = "immagini", length = 10)
    private String immagini;

    @CreationTimestamp
    @Column(name = "data_ins", nullable = false, updatable = false)
    private LocalDateTime dataIns;

    @UpdateTimestamp
    @Column(name = "data_mod", nullable = false)
    private LocalDateTime dataMod;
}