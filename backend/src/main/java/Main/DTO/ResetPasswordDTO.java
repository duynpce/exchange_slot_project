package Main.DTO;


import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor

public class ResetPasswordDTO {
    private String username;
    private String newPassword;
}
