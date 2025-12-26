package main.dto.Account;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class UpdateAccountDTO {
    @NotBlank(message = "blank studentCode")
    private String studentCode;
    @NotBlank(message = "blank classCode")
    private String classCode;
}
