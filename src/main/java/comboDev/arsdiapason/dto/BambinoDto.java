package comboDev.arsdiapason.dto;

import comboDev.arsdiapason.mybatis.model.Bambino;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.beans.BeanUtils;

@EqualsAndHashCode(callSuper = true)
@Data
public class BambinoDto extends Bambino {
    Integer idIstituto;
    Integer classe;
    String sezione;

    public static BambinoDto fromEntity(Bambino bambino, Integer idIstituto, Integer classe, String sezione) {
        if (bambino == null) return null;
        BambinoDto dto = new BambinoDto();
        BeanUtils.copyProperties(bambino, dto);
        dto.setIdIstituto(idIstituto);
        dto.setClasse(classe);
        dto.setSezione(sezione);
        return dto;
    }

}
