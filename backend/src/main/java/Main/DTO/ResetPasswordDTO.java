package Main.DTO;


import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor

public class ResetPasswordDTO {
    private String newPassword;
}
