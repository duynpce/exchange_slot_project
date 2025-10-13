package Main.Service;


import Main.DTO.LoginRequestDTO;
import Main.DTO.ResetPasswordDTO;
import Main.Exception.AccountException;
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
    AccountValidator accountValidator;


    public boolean register(Account account){
        accountValidator.validateRegister(account);

        String encryptedPassword = passwordEncoder.encode(account.getPassword());
        account.setPassword(encryptedPassword);
        accountRepository.save(account);

        return true;
    }

    public  Account login(LoginRequestDTO loginRequest){
        accountValidator.validateLogin(loginRequest);

        String username = loginRequest.getUsername();
        String password = loginRequest.getPassword();

        Account account = accountRepository.findByUsername(username).get(); ///has validated by validator
        String encryptedPassword = account.getPassword();
        boolean correctPassword = passwordEncoder.matches(password,encryptedPassword );

        if(correctPassword) {return account;}

        throw new AccountException("incorrect password" , HttpStatus.UNAUTHORIZED);

    }

    public boolean resetPassword(ResetPasswordDTO resetPasswordDTO){

        accountValidator.validateResetPassword(resetPasswordDTO);

        String username = resetPasswordDTO.getUsername();
        String encryptedPassword = passwordEncoder.encode(resetPasswordDTO.getNewPassword());

        return accountRepository.resetPassword(username,encryptedPassword) == 1;
    }

    public Account findByUserName(String userName){
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
