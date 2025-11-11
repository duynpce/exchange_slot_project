package Main.DTO.Auth;

import Main.Enum.Role;
import jakarta.persistence.Column;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RegisterRequestDTO {

    private  String username;
    private  String password;
    private  String phoneNumber;
    private String accountName;
    private String studentCode;
    private String classCode;
    private Role role;
}
