package comboDev.arsdiapason.model;

import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;

@Entity
@Table(name = "psicologo")
@Data
public class Psicologo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "nome", nullable = false, length = 50)
    private String nome;

    @Column(name = "cognome", nullable = false, length = 50)
    private String cognome;

    @CreationTimestamp
    @Column(name = "data_ins", nullable = false, updatable = false)
    private LocalDateTime dataIns;

    @UpdateTimestamp
    @Column(name = "data_mod", nullable = false)
    private LocalDateTime dataMod;
}