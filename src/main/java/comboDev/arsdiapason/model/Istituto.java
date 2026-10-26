package comboDev.arsdiapason.model;

import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;

@Entity
@Table(name = "istituto")
@Data
public class Istituto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "nome", nullable = false, length = 50)
    private String nome;

    @Column(name = "regione", nullable = false, length = 50)
    private String regione;

    @Column(name = "provincia", nullable = false, length = 50)
    private String provincia;

    @Column(name = "comune", nullable = false, length = 50)
    private String comune;

    @Column(name = "indirizzo", nullable = false, length = 50)
    private String indirizzo;

    @Column(name = "sezioni", nullable = false, length = 120)
    private String sezioni;

    @CreationTimestamp
    @Column(name = "data_ins", nullable = false, updatable = false)
    private LocalDateTime dataIns;

    @UpdateTimestamp
    @Column(name = "data_mod", nullable = false)
    private LocalDateTime dataMod;
}