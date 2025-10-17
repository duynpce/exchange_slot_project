package Main.RestController;

import Main.Config.UserDetailConfig;
import Main.DTO.*;
import Main.Exception.AccountException;
import Main.Model.Enity.Account;
import Main.Service.AccountService;
import Main.Utility.jwtUtil;
import Main.Utility.util;
import Main.Validator.AccountValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;




@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/")
public class AccountRestController {
    @Autowired
    AccountService accountService;

    @Autowired
    jwtUtil jwtUtility;

    @Autowired
    util utility;

    @Autowired
    AccountValidator accountValidator;

    @PostMapping("/register")
    public ResponseEntity<ResponseDTO<String>> register(@RequestBody Account account){
        accountValidator.validateRegister(account);// will put it in service if separate interface
        accountService.register(account);

        ResponseDTO<String> responseDTO = new ResponseDTO<>(true,"no error", "register successfully",null);

        return ResponseEntity.status(HttpStatus.CREATED).body(responseDTO);
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponseDTO> login(@RequestBody LoginRequestDTO loginRequest) {
        accountValidator.validateLogin(loginRequest);// will put it in service if separate interface
        Account foundAccount = accountService.login(loginRequest); ///login failed --> throw exception in service

        LoginResponseDTO loginResponseDTO;
        UserDetailConfig user = new UserDetailConfig(foundAccount);
        String refreshToken = jwtUtility.getRefreshToken(user);
        String accessToken = jwtUtility.getAccessToken(user);
        loginResponseDTO =  new LoginResponseDTO(refreshToken,accessToken ,"login success");
        return ResponseEntity.status(HttpStatus.OK).body(loginResponseDTO);
    }

    @PatchMapping("/reset_password")
    public ResponseEntity<ResponseDTO<String>> resetPassword
            (@RequestBody ResetPasswordDTO resetPasswordDTO){
        accountValidator.validateResetPassword(resetPasswordDTO);// will put it in service if separate interface
        accountService.resetPassword(resetPasswordDTO);

        ResponseDTO<String> responseDTO =
                new ResponseDTO<>(true,"no error","reset successfully",null);

        return ResponseEntity.status(HttpStatus.OK).body(responseDTO);
    }

    @PatchMapping("/account")
    public ResponseEntity<ResponseDTO<String>> patchAccount(@RequestBody PatchAccountDTO patchAccountDTO){
        Account accountAfterPatched = accountValidator.validatePatchAccount(patchAccountDTO);
        accountService.save(accountAfterPatched);

        ResponseDTO<String> responseDTO = new ResponseDTO<>
                        (true,"no error","patch account successfully",null);

        return ResponseEntity.status(HttpStatus.OK).body(responseDTO);
    }

}
