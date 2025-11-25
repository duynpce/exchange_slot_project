package Main.Controller;

import Main.DTO.Account.GetAccountDTO;
import Main.DTO.Account.UpdateAccountDTO;
import Main.DTO.Common.ResponseDTO;
import Main.Exception.BaseException;
import Main.Mapper.AccountMapper;
import Main.Entity.Account;
import Main.Service.AccountService;
import Main.Utility.JwtUtil;
import Main.Validator.AuthValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;




@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/")
@RequiredArgsConstructor
public class AccountController {
    private final AccountService accountService;
    private final JwtUtil jwtUtil;
    private final AuthValidator authValidator;
    private final AccountMapper accountMapper;


    @PatchMapping("/account")
    public ResponseEntity<ResponseDTO<String>> update(@RequestBody UpdateAccountDTO updateAccountDTO){
        final String username = jwtUtil.getUsername();
        Account account = accountService.findByUserName(username);
        authValidator.validateUpdateAccount(updateAccountDTO, account);
        accountService.update(account);

        ResponseDTO<String> responseDTO = new ResponseDTO<>
                        (true,"no error","patch account successfully",null);

        return ResponseEntity.status(HttpStatus.OK).body(responseDTO);
    }

    @GetMapping("/account")
    public ResponseEntity<ResponseDTO<GetAccountDTO>> getAccountByContextHold(){
        final String username = jwtUtil.getUsername(); ///get username in Context Holder(for security)

        if(username != null){
            GetAccountDTO getAccountDTO = accountMapper.toDto(accountService.findByUserName(username));

            ResponseDTO<GetAccountDTO> responseDTO =
                    new ResponseDTO<>(true,"no error","get account successfully",getAccountDTO);

            return ResponseEntity.status(HttpStatus.OK).body(responseDTO);
        }

        throw new BaseException("have not logged in", HttpStatus.UNAUTHORIZED);
    }






}
