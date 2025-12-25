package main.dto.Account;

import main.constant.Role;
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
    private String email;
    private Role role;
}
