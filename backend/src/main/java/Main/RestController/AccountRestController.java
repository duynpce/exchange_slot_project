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
    util utility;

    @Autowired
    AccountValidator accountValidator;

    @PostMapping("/register")
    public ResponseEntity<ResponseDTO<String>> register(@RequestBody Account account){
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
        final String username = utility.getUsername(); ///get username in Context Holder(for security)
        accountValidator.validateResetPassword(resetPasswordDTO,username);// will put it in service if separate interface
        accountService.resetPassword(resetPasswordDTO,username);

        ResponseDTO<String> responseDTO =
                new ResponseDTO<>(true,"no error","reset successfully",null);

        return ResponseEntity.status(HttpStatus.OK).body(responseDTO);
    }

    @PatchMapping("/account")
    public ResponseEntity<ResponseDTO<String>> patchAccount(@RequestBody PatchAccountDTO patchAccountDTO){
        Account accountAfterPatched = accountValidator.validatePatchAccount(patchAccountDTO);// will put it in service if separate interface
        accountService.save(accountAfterPatched);

        ResponseDTO<String> responseDTO = new ResponseDTO<>
                        (true,"no error","patch account successfully",null);

        return ResponseEntity.status(HttpStatus.OK).body(responseDTO);
    }

    @PatchMapping("/reset_password_for_testing")
    public ResponseEntity<ResponseDTO<String>> resetPasswordForTestTing(@RequestBody ResetPasswordDTO resetPasswordDTO){
        final String username = utility.getUsername(); ///get username in Context Holder(for security)
        accountService.resetPassword(resetPasswordDTO,username);

        ResponseDTO<String> responseDTO =
                new ResponseDTO<>(true,"no error","reset successfully",null);

        return ResponseEntity.status(HttpStatus.OK).body(responseDTO);
    }



}
