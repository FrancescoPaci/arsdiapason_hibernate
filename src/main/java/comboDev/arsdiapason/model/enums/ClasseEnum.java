package comboDev.arsdiapason.model.enums;

import com.fasterxml.jackson.annotation.JsonValue;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ClasseEnum {

    PRIMA("Prima"),
    SECONDA("Seconda"),
    TERZA("Terza"),
    QUARTA("Quarta"),
    QUINTA("Quinta");

    private final String value;

    // @JsonValue fa sì che, quando l'enum viene convertito in JSON per Angular,
    // venga letta direttamente la stringa ("Prima") anziché l'intero oggetto.
    @JsonValue
    public String getValue() {
        return value;
    }
}