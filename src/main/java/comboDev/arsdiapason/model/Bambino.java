package comboDev.arsdiapason.model;

import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "bambino")
@Data
public class Bambino {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "nome", nullable = false, length = 50)
    private String nome;

    @Column(name = "cognome", nullable = false, length = 50)
    private String cognome;

    @Column(name = "sesso", nullable = false, length = 1)
    private String sesso;

    @Column(name = "data_nascita", nullable = false)
    private LocalDate dataNascita;

    @Column(name = "id_istituto", nullable = false)
    private Integer idIstituto;

    @Column(name = "classe", nullable = false)
    private Integer classe;

    @Column(name = "sezione", nullable = false, length = 3)
    private String sezione;

    @Column(name = "anno_scolastico", nullable = false, length = 9)
    private String annoScolastico;

    @Column(name = "comune_nascita", length = 30)
    private String comuneNascita;

    @Column(name = "comune_residenza", length = 30)
    private String comuneResidenza;

    @Column(name = "indirizzo_residenza", length = 50)
    private String indirizzoResidenza;

    @Column(name = "numero_fratelli")
    private Byte numeroFratelli;

    @Column(name = "numero_sorelle")
    private Byte numeroSorelle;

    @Column(name = "ordine_genitura")
    private Byte ordineGenitura;

    @Column(name = "lavoro_padre", length = 50)
    private String lavoroPadre;

    @Column(name = "lavoro_madre", length = 50)
    private String lavoroMadre;

    @Column(name = "titolo_studio_padre", length = 50)
    private String titoloStudioPadre;

    @Column(name = "titolo_studio_madre", length = 50)
    private String titoloStudioMadre;

    @Column(name = "figlio_adottivo")
    private Boolean figlioAdottivo;

    @Column(name = "test_finito", nullable = false)
    private Boolean testFinito = false;

    @Column(name = "data_test")
    private LocalDate dataTest;

    @Column(name = "note", length = 300)
    private String note;

    @CreationTimestamp
    @Column(name = "data_ins", nullable = false, updatable = false)
    private LocalDateTime dataIns;

    @UpdateTimestamp
    @Column(name = "data_mod", nullable = false)
    private LocalDateTime dataMod;
}