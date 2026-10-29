package comboDev.arsdiapason.dto;

import comboDev.arsdiapason.model.Bambino;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.beans.BeanUtils;

@EqualsAndHashCode(callSuper = true)
@Data
public class BambinoDto extends Bambino {
    Integer idRelIstitutoBambino;
    Integer idIstituto;
    Integer classe;
    String sezione;

    public static BambinoDto fromEntity(Bambino bambino,
                                        Integer idRelIstitutoBambino,
                                        Integer idIstituto,
                                        Integer classe,
                                        String sezione) {
        if (bambino == null) return null;
        BambinoDto dto = new BambinoDto();
        BeanUtils.copyProperties(bambino, dto);
        dto.setIdRelIstitutoBambino(idRelIstitutoBambino);
        dto.setIdIstituto(idIstituto);
        dto.setClasse(classe);
        dto.setSezione(sezione);
        return dto;
    }

    public static Bambino toEntity(BambinoDto bambinoDto) {
        if (bambinoDto == null) return null;
        Bambino bambino = new Bambino();
        BeanUtils.copyProperties(bambinoDto, bambino);
        return bambino;
    }

}
