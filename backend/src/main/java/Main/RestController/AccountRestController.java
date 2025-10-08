package Main.RestController;

import Main.Config.UserDetailConfig;
import Main.DTO.LoginResponseDTO;
import Main.DTO.LoginRequestDTO;
import Main.DTO.ResetPasswordDTO;
import Main.DTO.ResponseDTO;
import Main.Enum.Role;
import Main.Exception.AccountException;
import Main.Model.Enity.Account;
import Main.Repository.AccountRepository;
import Main.Service.AccountService;
import Main.Utility.jwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import Main.Utility.util;

import java.util.Random;

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
    AccountRepository accountRepository;

    @PostMapping("/register")
    public ResponseEntity<ResponseDTO<String>> register(@RequestBody Account account){
        boolean emptyUserName = account.getUsername() == null;
        boolean emptyPassword = account.getPassword() == null;
        boolean emptyPhoneNumber = account.getPhoneNumber() == null;
        boolean emptyStudentCode = account.getStudentCode() == null;
        boolean emptyAccountName = account.getAccountName() == null;
        boolean emptyRole = account.getRole() == null;
        ///use validated here
        if(emptyUserName || emptyPassword || emptyPhoneNumber || emptyStudentCode || emptyAccountName || emptyRole){
            throw new AccountException("there's a null value assign to a not null field",HttpStatus.BAD_REQUEST);
        }

        boolean isValidPassword = utility.validatePassword(account.getPassword());

        if(!isValidPassword) {throw new AccountException("invalid password", HttpStatus.BAD_REQUEST); }

        boolean registerSuccess = accountService.register(account);
        ResponseDTO<String> responseDTO = new ResponseDTO<>();
        responseDTO.setProcessSuccess(registerSuccess);

        if(registerSuccess){
            responseDTO.setError("no error");
            responseDTO.setMessage("registered successfully");
            responseDTO.setHttpStatus(HttpStatus.CREATED.value());
        }

        responseDTO.setData("no data");
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(responseDTO);
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponseDTO> login(@RequestBody LoginRequestDTO loginRequest) {

        String username = loginRequest.getUsername();
        String password = loginRequest.getPassword();

        boolean emptyUserName = username == null;
        boolean emptyPassword = password == null;

        if(emptyUserName || emptyPassword){
            throw new AccountException("null password or username",HttpStatus.BAD_REQUEST);
        }


        LoginResponseDTO loginResponseDTO ;
        Account foundAccount = accountService.findByUserName(username);

        boolean loginSuccess = foundAccount != null;

        if(loginSuccess){
            UserDetailConfig user = new UserDetailConfig(foundAccount);
            String refreshToken = jwtUtility.getRefreshToken(user);
            String accessToken = jwtUtility.getAccessToken(user);
            loginResponseDTO =  new LoginResponseDTO(refreshToken,accessToken ,"login success");
            return  ResponseEntity.status(HttpStatus.OK).body(loginResponseDTO);
        }

        loginResponseDTO = new LoginResponseDTO("no refresh token", "no access token", "login failed, please check user name or password");
        return  ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(loginResponseDTO);
    }

    @PatchMapping("/reset_password")
    public ResponseEntity<ResponseDTO<String>> resetPassword
            (@RequestBody ResetPasswordDTO resetPasswordDTO){

        String newPassword = resetPasswordDTO.getNewPassword();
        boolean isValidPassword = utility.validatePassword(newPassword);

        if(!isValidPassword) {throw new AccountException("invalid password", HttpStatus.BAD_REQUEST); }

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

    @GetMapping ("/test/{username}/{password}") /// temporary for testing
    public void test(@PathVariable String username, @PathVariable String password){
        Random random = new Random();
        Account account = new Account(
                username, password ,
                String.valueOf(random.nextInt(100000)),
                String.valueOf(random.nextInt(100000)),
                String.valueOf(random.nextInt(100000)),
                Role.USER);
        accountService.register(account);
    }

}
