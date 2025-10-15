package Main.RestController;

import Main.Config.UserDetailConfig;
import Main.DTO.LoginResponseDTO;
import Main.DTO.LoginRequestDTO;
import Main.DTO.ResetPasswordDTO;
import Main.DTO.ResponseDTO;
import Main.Exception.AccountException;
import Main.Model.Enity.Account;
import Main.Service.AccountService;
import Main.Utility.jwtUtil;
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

    @PostMapping("/register")
    public ResponseEntity<ResponseDTO<String>> register(@RequestBody Account account){


        boolean registerSuccess = accountService.register(account);
        ResponseDTO<String> responseDTO = new ResponseDTO<>();
        responseDTO.setProcessSuccess(registerSuccess);
        responseDTO.setError("no error");
        responseDTO.setMessage("registered successfully");
        responseDTO.setHttpStatus(HttpStatus.CREATED.value());

        return ResponseEntity.status(HttpStatus.CREATED).body(responseDTO);
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponseDTO> login(@RequestBody LoginRequestDTO loginRequest) {

        LoginResponseDTO loginResponseDTO ;
        Account foundAccount = accountService.login(loginRequest); ///login failed --> throw exception in service

        UserDetailConfig user = new UserDetailConfig(foundAccount);
        String refreshToken = jwtUtility.getRefreshToken(user);
        String accessToken = jwtUtility.getAccessToken(user);
        loginResponseDTO =  new LoginResponseDTO(refreshToken,accessToken ,"login success");
        return ResponseEntity.status(HttpStatus.OK).body(loginResponseDTO);

    }

    @PatchMapping("/reset_password")
    public ResponseEntity<ResponseDTO<String>> resetPassword
            (@RequestBody ResetPasswordDTO resetPasswordDTO){

        boolean resetPasswordSuccess = accountService.resetPassword(resetPasswordDTO);
        ResponseDTO<String> responseDTO = new ResponseDTO<>();
        responseDTO.setProcessSuccess(resetPasswordSuccess);
        responseDTO.setData("no data");

        if(resetPasswordSuccess){
            responseDTO.setError("no error");
            responseDTO.setMessage("reset successfully");
            responseDTO.setHttpStatus(HttpStatus.OK.value());
            return ResponseEntity.status(HttpStatus.OK).body(responseDTO);
        }

        throw new AccountException("no existed account", HttpStatus.NOT_FOUND);

    }


}
