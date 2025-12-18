package Main.DTO.ExchangeSlotRequest;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreateExchangeSlotRequestDTO {
    @NotBlank(message = "blank studentCode")
    private String studentCode;
    @NotBlank(message = "blank desiredSlot")
    private String desiredSlot;
}
