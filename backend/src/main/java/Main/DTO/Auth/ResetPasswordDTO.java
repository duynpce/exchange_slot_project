package Main.DTO.Auth;


import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor

public class ResetPasswordDTO {
    private String newPassword;
}
