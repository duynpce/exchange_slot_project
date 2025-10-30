package Main.Validator;

import Main.DTO.Auth.LoginRequestDTO;
import Main.DTO.Account.UpdateAccountDTO;
import Main.DTO.Auth.ResetPasswordDTO;
import Main.Exception.BaseException;
import Main.Entity.Account;
import Main.Service.AccountService;
import Main.Service.MajorClassService;
import Main.Utility.jwtUtil;
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

    @Autowired
    jwtUtil jwtUtility;

    public void validateRegister(Account account){

        utility.throwExceptionIfNull(account.getUsername(),"null username");
        utility.throwExceptionIfNull(account.getPassword(),"null password");
        utility.throwExceptionIfNull(account.getPhoneNumber(), "null phone number");
        utility.throwExceptionIfNull(account.getStudentCode(), "null student code");
        utility.throwExceptionIfNull(account.getAccountName(), "null Account name");
        utility.throwExceptionIfNull(account.getClassCode(), "null class code");
        utility.throwExceptionIfNull(account.getRole().toString(), "null role");

        utility.throwExceptionIfExists(accountService.existsByUsername(account.getUsername()), "existed username");
        utility.throwExceptionIfExists(accountService.existsByPhoneNumber(account.getPhoneNumber()), "existed phone number");
        utility.throwExceptionIfExists(accountService.existsByStudentCode(account.getStudentCode()), "existed student code");
        utility.throwExceptionIfExists(accountService.existsByAccountName(account.getAccountName()), "existed account name");

        utility.throwExceptionIfNotExists(majorClassService.existsByClassCode(account.getClassCode()),
                "not class with code: " + account.getClassCode() );
        boolean isValidPassword = utility.validatePassword(account.getPassword());

        if(!isValidPassword) {throw new BaseException("invalid password", HttpStatus.BAD_REQUEST); }
    }

    public void validateLogin(LoginRequestDTO loginRequest){
        final String username = loginRequest.getUsername();
        final String password = loginRequest.getPassword();;

        utility.throwExceptionIfNull(username, "null username");
        utility.throwExceptionIfNull(password, "null password");
        utility.throwExceptionIfNotExists(accountService.existsByUsername(username),"no account with username " + username);
    }

    public void validateResetPassword(ResetPasswordDTO resetPasswordDTO, String username){
        final String newPassword = resetPasswordDTO.getNewPassword();

        utility.throwExceptionIfNull(username, "null username");
        utility.throwExceptionIfNull(newPassword, "null newPassword");

        boolean isValidPassword = utility.validatePassword(newPassword);

        if(!isValidPassword) {throw new BaseException("invalid password", HttpStatus.BAD_REQUEST); }

        utility.throwExceptionIfNotExists(accountService.existsByUsername(username),"no account with username " + username);
    }

    //validate and set new information for  account
    public void validateUpdateAccount(UpdateAccountDTO updateAccountDTO, Account account){
        final String newStudentCode = updateAccountDTO.getStudentCode();
        final String newClassCode = updateAccountDTO.getClassCode();

        if(newStudentCode  == null && newClassCode == null){
            throw new BaseException("null both new student code and new class code", HttpStatus.BAD_REQUEST);
        }

        if(newClassCode != null){// if not null --> set new
            account.setClassCode(newClassCode);
            utility.throwExceptionIfNotExists(majorClassService.existsByClassCode(newClassCode),"no class with class code: " +newClassCode );
        }

        if(newStudentCode != null){
            account.setStudentCode(newStudentCode);
        }


    }
}
