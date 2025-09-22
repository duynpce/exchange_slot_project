package Main.RestController;

import Main.DTO.LoginDTO;
import Main.DTO.ResponseDTO;
import Main.Model.Enity.Account;
import Main.Repository.AccountRepository;
import Main.Service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import Main.Utility.Utility;

import java.util.Optional;
@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/account")
public class AccountRestController {
    @Autowired
    AccountService accountService;

    @Autowired
   Utility utility;

    @Autowired
    AccountRepository accountRepository;



    @PostMapping("/register")
    public ResponseEntity<ResponseDTO<String>> register(@RequestBody Account account){/// sai
        boolean registerSuccess = accountService.register(account);
        ResponseDTO<String> responseDTO = new ResponseDTO<>();
        responseDTO.setProcessSuccess(registerSuccess);

        if(registerSuccess){
            responseDTO.setError("no error");
            responseDTO.setMessage("registered successfully");
            responseDTO.setHttpStatus(HttpStatus.CREATED.value());
        }else{
            responseDTO.setError("existed account or phone number or account name ");
            responseDTO.setMessage("registered failed");
            responseDTO.setHttpStatus(HttpStatus.CONFLICT.value());
        }
        responseDTO.setData("no data");
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(responseDTO);
    }

    @PostMapping("/login")
    public ResponseEntity<LoginDTO> login(@RequestBody Account account) {

        LoginDTO loginDTO;
        boolean loginSuccess = accountService.login(account);

        if(loginSuccess){
            loginDTO =  new LoginDTO(utility.getRefreshToken(account.getUserName()), utility.getAccessToken(account.getUserName()),"login success");
            return  ResponseEntity.status(HttpStatus.OK).body(loginDTO);
        }

        loginDTO = new LoginDTO("no refresh token", "no access token", "login failed, please check user name or password");
        return  ResponseEntity.status(HttpStatus.OK).body(loginDTO);
    }

    @PatchMapping("/reset_password")
    public ResponseEntity<ResponseDTO<String>> resetPassword(@RequestBody Account accountWithNewPassword){

        boolean resetPasswordSuccess = accountService.resetPassword(accountWithNewPassword);
        ResponseDTO<String> responseDTO = new ResponseDTO<>();
        responseDTO.setProcessSuccess(resetPasswordSuccess);
        responseDTO.setData("no data");

        if(resetPasswordSuccess){
            responseDTO.setError("no error");
            responseDTO.setMessage("reset successfully");
            responseDTO.setHttpStatus(HttpStatus.OK.value());
            return ResponseEntity.status(HttpStatus.OK).body(responseDTO);
        }

        responseDTO.setError("no existed account");
        responseDTO.setMessage("reset failed");
        responseDTO.setHttpStatus(HttpStatus.NOT_FOUND.value());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(responseDTO);

    }

    @GetMapping("/test/{userName}")
    public Account test(@PathVariable String userName){
        Optional<Account> account = accountRepository.findByUserName(userName);;
        return account.orElse(null);
    }

}
