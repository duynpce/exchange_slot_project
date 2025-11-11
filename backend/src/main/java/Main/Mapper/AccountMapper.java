package Main.Mapper;

import Main.DTO.Account.GetAccountDTO;
import Main.DTO.Account.UpdateAccountDTO;
import Main.DTO.Auth.RegisterRequestDTO;
import Main.Entity.Account;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface AccountMapper {
    GetAccountDTO toDto(Account account);
    Account toEntity(RegisterRequestDTO registerRequestDTO);
    Account toEntity(UpdateAccountDTO updateAccountDTO);
}
