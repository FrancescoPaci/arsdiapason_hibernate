package comboDev.arsdiapason.model;

import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;

@Entity
@Table(name = "rel_istituto_bambino")
@Data
public class RelIstitutoBambino {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "id_istituto")
    private Integer idIstituto;

    @Column(name = "id_bambino")
    private Integer idBambino;

    @Column(name = "classe", nullable = false)
    private Integer classe;

    @Column(name = "sezione", nullable = false, length = 3)
    private String sezione;

    @CreationTimestamp
    @Column(name = "data_ins", nullable = false, updatable = false)
    private LocalDateTime dataIns;

    @UpdateTimestamp
    @Column(name = "data_mod", nullable = false)
    private LocalDateTime dataMod;
}