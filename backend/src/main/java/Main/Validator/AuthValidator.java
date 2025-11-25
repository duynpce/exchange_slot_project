package Main.Validator;

import Main.DTO.Auth.LoginRequestDTO;
import Main.DTO.Account.UpdateAccountDTO;
import Main.DTO.Auth.ResetPasswordDTO;
import Main.Exception.BaseException;
import Main.Entity.Account;
import Main.Service.AccountService;
import Main.Service.MajorClassService;
import Main.Utility.Util;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class AuthValidator {

    private  final Util util;
    private  final AccountService accountService;
    private  final MajorClassService majorClassService;

    public void validateRegister(Account account){

        util.throwExceptionIfNull(account.getUsername(),"null username");
        util.throwExceptionIfNull(account.getPassword(),"null password");
        util.throwExceptionIfNull(account.getPhoneNumber(), "null phone number");
        util.throwExceptionIfNull(account.getStudentCode(), "null student code");
        util.throwExceptionIfNull(account.getAccountName(), "null Account name");
        util.throwExceptionIfNull(account.getClassCode(), "null class code");
        util.throwExceptionIfNull(account.getRole().toString(), "null role");

        util.throwExceptionIfExists(accountService.existsByUsername(account.getUsername()), "existed username");
        util.throwExceptionIfExists(accountService.existsByPhoneNumber(account.getPhoneNumber()), "existed phone number");
        util.throwExceptionIfExists(accountService.existsByStudentCode(account.getStudentCode()), "existed student code");
        util.throwExceptionIfExists(accountService.existsByAccountName(account.getAccountName()), "existed account name");

        util.throwExceptionIfNotExists(majorClassService.existsByClassCode(account.getClassCode()),
                "not class with code: " + account.getClassCode() );
        boolean isValidPassword = util.validatePassword(account.getPassword());

        if(!isValidPassword) {throw new BaseException("invalid password", HttpStatus.BAD_REQUEST); }
    }

    public void validateLogin(LoginRequestDTO loginRequest){
        final String username = loginRequest.getUsername();
        final String password = loginRequest.getPassword();;

        util.throwExceptionIfNull(username, "null username");
        util.throwExceptionIfNull(password, "null password");
        util.throwExceptionIfNotExists(accountService.existsByUsername(username),"no account with username " + username);
    }

    public void validateResetPassword(ResetPasswordDTO resetPasswordDTO, String username){
        final String newPassword = resetPasswordDTO.getNewPassword();

        util.throwExceptionIfNull(username, "null username");
        util.throwExceptionIfNull(newPassword, "null newPassword");

        boolean isValidPassword = util.validatePassword(newPassword);

        if(!isValidPassword) {throw new BaseException("invalid password", HttpStatus.BAD_REQUEST); }

        util.throwExceptionIfNotExists(accountService.existsByUsername(username),"no account with username " + username);
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
            util.throwExceptionIfNotExists(majorClassService.existsByClassCode(newClassCode),"no class with class code: " +newClassCode );
        }

        if(newStudentCode != null){
            account.setStudentCode(newStudentCode);
        }


    }
}
