package Main.Controller;

import Main.DTO.Account.GetAccountDTO;
import Main.DTO.Account.UpdateAccountDTO;
import Main.DTO.Auth.*;
import Main.DTO.Common.ResponseDTO;
import Main.Exception.BaseException;
import Main.Mapper.AccountMapper;
import Main.Entity.Account;
import Main.Service.AccountService;
import Main.Utility.jwtUtil;
import Main.Validator.AccountValidator;
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
    private final jwtUtil jwtUtility;
    private final AccountValidator accountValidator;
    private final AccountMapper accountMapper;

    @PostMapping("/register")
    public ResponseEntity<ResponseDTO<String>> register(@RequestBody RegisterRequestDTO registerRequestDTO){
        Account account = accountMapper.toEntity(registerRequestDTO);
        accountValidator.validateRegister(account);// will put it in service if separate interface
        accountService.register(account);

        ResponseDTO<String> responseDTO = new ResponseDTO<>
                (true,"no error", "register successfully",null);

        return ResponseEntity.status(HttpStatus.CREATED).body(responseDTO);
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponseDTO> login(@RequestBody LoginRequestDTO loginRequest) {
        accountValidator.validateLogin(loginRequest);// will put it in service if separate interface
        LoginResponseDTO loginResponseDTO = accountService.login(loginRequest);

        return ResponseEntity.status(HttpStatus.OK).body(loginResponseDTO);
    }

    @PatchMapping("/reset_password")
    public ResponseEntity<ResponseDTO<String>> resetPassword
            (@RequestBody ResetPasswordDTO resetPasswordDTO){
        final String username = jwtUtility.getUsername(); ///get username in Context Holder(for security)
        accountValidator.validateResetPassword(resetPasswordDTO,username);// will put it in service if separate interface
        accountService.resetPassword(resetPasswordDTO,username);

        ResponseDTO<String> responseDTO =
                new ResponseDTO<>(true,"no error","reset successfully",null);

        return ResponseEntity.status(HttpStatus.OK).body(responseDTO);
    }

    @PostMapping("/refresh_access_token")
    public ResponseEntity<ResponseDTO<ResponseRefreshTokenDTO>>
    refreshAccessToken(@RequestBody RefreshAccessTokenDTO refreshAccessTokenDTO){
        ResponseRefreshTokenDTO responseRefreshTokenDTO = accountService.refreshAccessToken(refreshAccessTokenDTO);

        ResponseDTO<ResponseRefreshTokenDTO> response = new ResponseDTO<>(true,
                "no error", "refresh access token successfully", responseRefreshTokenDTO);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }


    @PatchMapping("/account")
    public ResponseEntity<ResponseDTO<String>> update(@RequestBody UpdateAccountDTO updateAccountDTO){
        final String username = jwtUtility.getUsername();
        Account account = accountService.findByUserName(username);
        accountValidator.validateUpdateAccount(updateAccountDTO, account);
        accountService.update(account);

        ResponseDTO<String> responseDTO = new ResponseDTO<>
                        (true,"no error","patch account successfully",null);

        return ResponseEntity.status(HttpStatus.OK).body(responseDTO);
    }

    @GetMapping("/account")
    public ResponseEntity<ResponseDTO<GetAccountDTO>> getAccountByContextHold(){
        final String username = jwtUtility.getUsername(); ///get username in Context Holder(for security)

        if(username != null){
            GetAccountDTO getAccountDTO = accountMapper.toDto(accountService.findByUserName(username));

            ResponseDTO<GetAccountDTO> responseDTO =
                    new ResponseDTO<>(true,"no error","get account successfully",getAccountDTO);

            return ResponseEntity.status(HttpStatus.OK).body(responseDTO);
        }

        throw new BaseException("have not logged in", HttpStatus.UNAUTHORIZED);
    }


    @PatchMapping("/reset_password_for_testing")
    public ResponseEntity<ResponseDTO<String>> resetPasswordForTestTing(@RequestBody ResetPasswordDTO resetPasswordDTO){
        final String username = jwtUtility.getUsername(); ///get username in Context Holder(for security)
        accountService.resetPassword(resetPasswordDTO,username);

        ResponseDTO<String> responseDTO =
                new ResponseDTO<>(true,"no error","reset successfully",null);

        return ResponseEntity.status(HttpStatus.OK).body(responseDTO);
    }



}
