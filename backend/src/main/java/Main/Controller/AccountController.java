package Main.Controller;

import Main.DTO.Account.GetAccountDTO;
import Main.DTO.Account.UpdateAccountDTO;
import Main.DTO.Common.ResponseDTO;
import Main.Exception.BaseException;
import Main.Mapper.AccountMapper;
import Main.Entity.Account;
import Main.Service.AccountService;
import Main.Utility.JwtUtil;
import Main.Validator.AccountValidator;
import Main.Validator.AuthValidator;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;




@RestController
@RequestMapping("/account")
@RequiredArgsConstructor
public class AccountController {
    private final AccountService accountService;
    private final JwtUtil jwtUtil;
    private final AccountValidator accountValidator;
    private final AccountMapper accountMapper;


    @PatchMapping
    public ResponseEntity<ResponseDTO<String>> update(@Valid @RequestBody UpdateAccountDTO updateAccountDTO){
        final String username = jwtUtil.getUsername();
        Account account = accountService.findByUsername(username);
        accountValidator.validateUpdateAccount(updateAccountDTO, account);
        accountService.save(account);

        ResponseDTO<String> responseDTO = new ResponseDTO<>
                        (true,"no error","patch account successfully",null);

        return ResponseEntity.status(HttpStatus.OK).body(responseDTO);
    }

    @GetMapping
    public ResponseEntity<ResponseDTO<GetAccountDTO>> getAccountByContextHold(){
        final String username = jwtUtil.getUsername(); ///get username in Context Holder(for security)

        if(username != null){
            GetAccountDTO getAccountDTO = accountMapper.toDto(accountService.findByUsername(username));

            ResponseDTO<GetAccountDTO> responseDTO =
                    new ResponseDTO<>(true,"no error","get account successfully",getAccountDTO);

            return ResponseEntity.status(HttpStatus.OK).body(responseDTO);
        }

        throw new BaseException("have not logged in", HttpStatus.UNAUTHORIZED);
    }






}
