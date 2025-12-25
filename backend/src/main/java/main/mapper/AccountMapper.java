package main.mapper;

import main.dto.Account.GetAccountDTO;
import main.dto.Account.UpdateAccountDTO;
import main.dto.Auth.RegisterRequestDTO;
import main.entity.Account;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface AccountMapper {
    GetAccountDTO toDto(Account account);
    Account toEntity(RegisterRequestDTO registerRequestDTO);
    Account toEntity(UpdateAccountDTO updateAccountDTO);
}
