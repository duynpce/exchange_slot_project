package Main.Service;


import Main.Config.UserDetailConfig;
import Main.DTO.LoginRequestDTO;
import Main.DTO.LoginResponseDTO;
import Main.DTO.PatchAccountDTO;
import Main.DTO.ResetPasswordDTO;
import Main.Exception.AccountException;
import Main.Utility.jwtUtil;
import Main.Utility.util;
import Main.Validator.AccountValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import java.util.Optional;

import Main.Model.Enity.Account;
import Main.Repository.AccountRepository;

@Service
public class AccountService {

    @Autowired
    AccountRepository accountRepository;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    util utility;

    @Autowired
    jwtUtil jwtUtility;



    public void register(Account account){

        String encryptedPassword = passwordEncoder.encode(account.getPassword());
        account.setPassword(encryptedPassword);
        accountRepository.save(account);

    }

    public LoginResponseDTO login(LoginRequestDTO loginRequest){

        String username = loginRequest.getUsername();
        String password = loginRequest.getPassword();
        Account foundAccount = accountRepository.findByUsername(username)
                .orElseThrow(() -> new AccountException("Account not found", HttpStatus.UNAUTHORIZED));
        String encryptedPassword = foundAccount.getPassword();
        boolean correctPassword = passwordEncoder.matches(password,encryptedPassword );

        if(correctPassword) {
            LoginResponseDTO loginResponseDTO;
            UserDetailConfig user = new UserDetailConfig(foundAccount);
            String refreshToken = jwtUtility.getRefreshToken(user);
            String accessToken = jwtUtility.getAccessToken(user);
            loginResponseDTO =  new LoginResponseDTO(refreshToken,accessToken ,"login success");
            return loginResponseDTO;
        }

        throw new AccountException("incorrect password" , HttpStatus.UNAUTHORIZED);

    }

    public void resetPassword(ResetPasswordDTO resetPasswordDTO, String username){

        String encryptedPassword = passwordEncoder.encode(resetPasswordDTO.getNewPassword());
        accountRepository.resetPassword(username, encryptedPassword);
    }

    public void  save(Account account){
        accountRepository.save(account);
    }


    public Account findByUserName(String userName) {
        return accountRepository.findByUsername(userName).orElse(null);
    }

    public Account findByStudentCode(String studentCode){
        return  accountRepository.findByStudentCode(studentCode).orElse(null);
    }

    public boolean existsByStudentCode(String studentCode){
        return accountRepository.existsByStudentCode(studentCode);
    }

    public boolean existsByUsername(String username){
        return accountRepository.existsByUsername(username);
    }

    public boolean existsByAccountName(String accountName){
        return accountRepository.existsByAccountName(accountName);
    }

    public boolean existsByPhoneNumber(String phoneNumber){
        return accountRepository.existsByPhoneNumber(phoneNumber);
    }



}
