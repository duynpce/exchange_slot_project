package Main.Validator;

import Main.DTO.LoginRequestDTO;
import Main.DTO.ResetPasswordDTO;
import Main.Exception.AccountException;
import Main.Model.Enity.Account;
import Main.Repository.AccountRepository;
import Main.Utility.util;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

@Component
public class AccountValidator {

    @Autowired
    util utility;

    @Autowired /// temporary could separate interface for implementing versions of service
    AccountRepository accountRepository;

    void throwExceptionIfNull(String field, String message){
        if(field == null){
            throw new AccountException(message, HttpStatus.BAD_REQUEST);
        }
    }

    void throwExceptionIfExists(boolean isExists, String message){
        if(isExists){
            throw new AccountException(message, HttpStatus.CONFLICT);
        }
    }

    void throwExceptionIfNotExists(boolean isExists, String message){
        if(!isExists){
            throw new AccountException(message, HttpStatus.NOT_FOUND);
        }
    }

    public void validateRegister(Account account){

        throwExceptionIfNull(account.getUsername(),"null username");
        throwExceptionIfNull(account.getPassword(),"null password");
        throwExceptionIfNull(account.getPhoneNumber(), "null phone number");
        throwExceptionIfNull(account.getStudentCode(), "null student code");
        throwExceptionIfNull(account.getAccountName(), "null Account name");
        throwExceptionIfNull(account.getRole().toString(), "null role");

        throwExceptionIfExists(accountRepository.existsByUsername(account.getUsername()), "existed username");
        throwExceptionIfExists(accountRepository.existsByPhoneNumber(account.getPhoneNumber()), "existed phone number");
        throwExceptionIfExists(accountRepository.existsByStudentCode(account.getStudentCode()), "existed student code");
        throwExceptionIfExists(accountRepository.existsByAccountName(account.getAccountName()), "existed account name");

        boolean isValidPassword = utility.validatePassword(account.getPassword());

        if(!isValidPassword) {throw new AccountException("invalid password", HttpStatus.BAD_REQUEST); }
    }

    public void validateLogin(LoginRequestDTO loginRequest){
        final String username = loginRequest.getUsername();
        final String password = loginRequest.getPassword();;

        throwExceptionIfNull(username, "null username");
        throwExceptionIfNull(password, "null password");
        throwExceptionIfNotExists(accountRepository.existsByUsername(username),"no account with username " + username);
    }

    public void validateResetPassword(ResetPasswordDTO resetPasswordDTO){
        final String username = resetPasswordDTO.getUsername();;
        final String newPassword = resetPasswordDTO.getNewPassword();

        throwExceptionIfNull(username, "null username");
        throwExceptionIfNull(newPassword, "null newPassword");

        boolean isValidPassword = utility.validatePassword(newPassword);

        if(!isValidPassword) {throw new AccountException("invalid password", HttpStatus.BAD_REQUEST); }

        throwExceptionIfNotExists(accountRepository.existsByUsername(username),"no account with username " + username);
    }
}
