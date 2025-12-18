package Main.DTO.Account;

import lombok.Data;
import jakarta.validation.constraints.NotBlank;

@Data
public class UpdateAccountDTO {
    @NotBlank(message = "blank studentCode")
    private String studentCode;
    @NotBlank(message = "blank classCode")
    private String classCode;
}
