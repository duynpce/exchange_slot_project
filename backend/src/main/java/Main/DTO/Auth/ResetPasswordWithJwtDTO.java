package Main.DTO.Auth;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ResetPasswordWithJwtDTO {
    private String refreshToken;
    private String username;
    private String newPassword;
}
