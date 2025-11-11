package Main.DTO.Account;

import Main.Enum.Role;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GetAccountDTO {
    private int id;
    private String classCode;
    private String studentCode;
    private String accountName;
    private Role role;
}
