package Main.DTO.ExchangeSlotRequest;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreateExchangeSlotRequestDTO {

    private String studentCode;
    private String desiredSlot;
}
