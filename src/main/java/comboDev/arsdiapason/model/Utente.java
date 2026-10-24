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
@Table(name = "utente")
@Data
public class Utente {

    @Id
    @Column(name = "email", length = 40, nullable = false)
    private String email;

    @Column(name = "password", nullable = false, length = 75)
    private String password;

    @Column(name = "active", nullable = false)
    private Boolean active = false;

    @Column(name = "role", nullable = false, length = 10)
    private String role;

    @Column(name = "id_psicologo", nullable = false)
    private Integer idPsicologo;

    @Column(name = "temporary_code")
    private Integer temporaryCode;

    @CreationTimestamp
    @Column(name = "data_ins", nullable = false, updatable = false)
    private LocalDateTime dataIns;

    @UpdateTimestamp
    @Column(name = "data_mod", nullable = false)
    private LocalDateTime dataMod;
}