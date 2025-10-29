package Main.DTO.ExchangeSlotRequest;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ExchangeSlotRequestResponseDTO {
    private int id;
    private String studentCode;
    private String currentClassCode;
    private String desiredSlot;
    private String currentSlot;
}
