package Main.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ExchangeClassRequestResponseDTO {

    private int id;
    private String studentCode;
    private String desiredClassCode;
    private String currentClassCode;
    private String desiredSlot;
    private String currentSlot;

}
