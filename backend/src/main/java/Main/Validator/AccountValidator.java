package Main.Validator;

import Main.DTO.LoginRequestDTO;
import Main.DTO.PatchAccountDTO;
import Main.DTO.ResetPasswordDTO;
import Main.Exception.AccountException;
import Main.Model.Enity.Account;
import Main.Model.Enity.MajorClass;
import Main.Service.AccountService;
import Main.Service.AccountService;
import Main.Service.MajorClassService;
import Main.Utility.util;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

@Component
public class AccountValidator {

    @Autowired
    util utility;

    @Autowired 
    AccountService accountService;

    @Autowired
    MajorClassService majorClassService;


    private void throwExceptionIfNull(Object field, String message){
        if(field == null){
            throw new AccountException(message, HttpStatus.BAD_REQUEST);
        }
    }

    private void throwExceptionIfExists(boolean isExists, String message){
        if(isExists){
            throw new AccountException(message, HttpStatus.CONFLICT);
        }
    }

    private void throwExceptionIfNotExists(boolean isExists, String message){
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
        throwExceptionIfNull(account.getClassCode(), "null class code");
        throwExceptionIfNull(account.getRole().toString(), "null role");

        throwExceptionIfExists(accountService.existsByUsername(account.getUsername()), "existed username");
        throwExceptionIfExists(accountService.existsByPhoneNumber(account.getPhoneNumber()), "existed phone number");
        throwExceptionIfExists(accountService.existsByStudentCode(account.getStudentCode()), "existed student code");
        throwExceptionIfExists(accountService.existsByAccountName(account.getAccountName()), "existed account name");

        throwExceptionIfNotExists(majorClassService.existsByClassCode(account.getClassCode()),
                "not class with code: " + account.getClassCode() );
        boolean isValidPassword = utility.validatePassword(account.getPassword());

        if(!isValidPassword) {throw new AccountException("invalid password", HttpStatus.BAD_REQUEST); }
    }

    public void validateLogin(LoginRequestDTO loginRequest){
        final String username = loginRequest.getUsername();
        final String password = loginRequest.getPassword();;

        throwExceptionIfNull(username, "null username");
        throwExceptionIfNull(password, "null password");
        throwExceptionIfNotExists(accountService.existsByUsername(username),"no account with username " + username);
    }

    public void validateResetPassword(ResetPasswordDTO resetPasswordDTO){
        final String username = resetPasswordDTO.getUsername();;
        final String newPassword = resetPasswordDTO.getNewPassword();

        throwExceptionIfNull(username, "null username");
        throwExceptionIfNull(newPassword, "null newPassword");

        boolean isValidPassword = utility.validatePassword(newPassword);

        if(!isValidPassword) {throw new AccountException("invalid password", HttpStatus.BAD_REQUEST); }

        throwExceptionIfNotExists(accountService.existsByUsername(username),"no account with username " + username);
    }

    //validate and return account after patched
    public Account validatePatchAccount(PatchAccountDTO patchAccountDTO){ //a little multitask here
        final String username = utility.getUsername();
        final String newStudentCode = patchAccountDTO.getNewStudentCode();
        final String newClassCode = patchAccountDTO.getNewClassCode();
        Account account = accountService.findByUserName(username);

        throwExceptionIfNull(account , "no account with username: " + username + " ContextHolder");

        if(newStudentCode  == null && newClassCode == null){
            throw new AccountException("null both new student code and new class code", HttpStatus.BAD_REQUEST);
        }

        if(newStudentCode != null){
            account.setStudentCode(newStudentCode);
        }

        if(newClassCode != null){
            throwExceptionIfNotExists(majorClassService.existsByClassCode(newClassCode),
                    "not class with code: " + newClassCode);
            account.setClassCode(newClassCode);
        }

        return account; // this is account after patched
    }
}
