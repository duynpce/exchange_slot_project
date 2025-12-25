package main.dto.ExchangeClassRequest;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UpdateExchangeClassRequestDTO {
    @NotBlank(message = "blank desiredClassCode")
    private String desiredClassCode;
}
