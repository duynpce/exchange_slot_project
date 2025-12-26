// java
package main.dto.Auth;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ResetPasswordWithOtpDTO {
    @NotBlank(message = "blank email")
    @Email
    private String email;

    @NotBlank(message = "blank resetToken")
    private String resetToken;

    @NotBlank(message = "blank newPassword")
    private String newPassword;
}