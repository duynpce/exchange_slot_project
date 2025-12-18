// java
package Main.DTO.Auth;

import Main.Enum.Role;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RegisterRequestDTO {

    @NotBlank(message = "blank username")
    @Size(min = 6, message = "username's must be larger or equals 6")
    private String username;

    @NotBlank(message = "blank password")
    private String password;

    @NotBlank(message = "blank phoneNumber")
    private String phoneNumber;

    @NotBlank(message = "blank accountName")
    private String accountName;

    @NotBlank(message = "blank studentCode")
    private String studentCode;

    @NotBlank(message = "blank classCode")
    private String classCode;

    @NotBlank(message = "blank email")
    private String email;

}