package main.dto.ExchangeClassRequest;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreateExchangeClassRequestDTO {
    @NotBlank(message = "blank studentCode")
    private String studentCode;
    @NotBlank(message = "blank desiredClassCode")
    private String desiredClassCode;
}
