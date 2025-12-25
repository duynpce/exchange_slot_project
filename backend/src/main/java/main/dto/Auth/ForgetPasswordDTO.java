// java
package main.dto.Auth;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ForgetPasswordDTO {
    @NotBlank(message = "blank usernameOrEmail")
    private String usernameOrEmail;
}