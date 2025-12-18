// java
package Main.DTO.Auth;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ResetPasswordWithJwtDTO {
    @NotBlank(message = "blank refreshToken")
    private String refreshToken;

    @NotBlank(message = "blank username")
    @Size(min = 6, message = "username's must be larger or equals 6")
    private String username;

    @NotBlank(message = "blank newPassword")
    private String newPassword;
}