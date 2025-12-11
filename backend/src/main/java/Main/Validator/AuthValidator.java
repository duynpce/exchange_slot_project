package Main.Validator;

import Main.DTO.Auth.ForgetPasswordDTO;
import Main.DTO.Auth.LoginRequestDTO;
import Main.DTO.Auth.ResetPasswordDTO;
import Main.DTO.Auth.ResetPasswordWithJwtDTO;
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

        util.throwExceptionIfNull(account, "null account");
        util.throwExceptionIfNull(account.getUsername(),"null username");
        util.throwExceptionIfNull(account.getPassword(),"null password");
        util.throwExceptionIfNull(account.getPhoneNumber(), "null phone number");
        util.throwExceptionIfNull(account.getStudentCode(), "null student code");
        util.throwExceptionIfNull(account.getAccountName(), "null Account name");
        util.throwExceptionIfNull(account.getClassCode(), "null class code");
        util.throwExceptionIfNull(account.getRole().toString(), "null role");
        util.throwExceptionIfNull(account.getEmail(), "null email");

        util.throwExceptionIfExists(accountService.existsByUsername(account.getUsername()), "existed username");
        util.throwExceptionIfExists(accountService.existsByPhoneNumber(account.getPhoneNumber()), "existed phone number");
        util.throwExceptionIfExists(accountService.existsByStudentCode(account.getStudentCode()), "existed student code");
        util.throwExceptionIfExists(accountService.existsByAccountName(account.getAccountName()), "existed account name");
        util.throwExceptionIfExists(accountService.existsByEmail(account.getEmail()), "existed email");

        util.throwExceptionIfNotExists(majorClassService.existsByClassCode(account.getClassCode()),
                "not class with code: " + account.getClassCode() );
        boolean isValidPassword = util.validatePassword(account.getPassword());

        if(!isValidPassword) {throw new BaseException("invalid password", HttpStatus.BAD_REQUEST); }
    }

    public void validateLogin(LoginRequestDTO loginRequest){

        final String username = loginRequest.getUsername();
        final String password = loginRequest.getPassword();;

        util.throwExceptionIfNull(loginRequest, "null login request");
        util.throwExceptionIfNull(username, "null username");
        util.throwExceptionIfNull(password, "null password");
        util.throwExceptionIfNotExists(accountService.existsByUsername(username),"no account with username " + username);
    }

    //return email of account
    public String validateForgetPassword(ForgetPasswordDTO forgetPasswordDTO){

        util.throwExceptionIfNull(forgetPasswordDTO, "null forgetPasswordDTO");
        util.throwExceptionIfNull(forgetPasswordDTO.getUsernameOrEmail(), "null input");

        Account account;

        final String usernameOrEmail = forgetPasswordDTO.getUsernameOrEmail();
        if(util.isEmail(usernameOrEmail)){
           account =  accountService.findByEmail(usernameOrEmail);

        }else {
            account = accountService.findByUsername(usernameOrEmail);
        }


        return account.getEmail();

    }

    public void validateResetPasswordWithOtp(ResetPasswordDTO resetPasswordDTO){
        final String newPassword = resetPasswordDTO.getNewPassword();
        final String email = resetPasswordDTO.getEmail();
        final String resetToken = resetPasswordDTO.getResetToken();

        util.throwExceptionIfNull(email, "null email");
        util.throwExceptionIfNull(newPassword, "null newPassword");
        util.throwExceptionIfNull(resetToken, "null resetToken");

        boolean isValidPassword = util.validatePassword(newPassword);

        if(!isValidPassword) {throw new BaseException("invalid password", HttpStatus.BAD_REQUEST); }

        util.throwExceptionIfNotExists(accountService.existsByEmail(email),"no account with email: " + email);
    }

    public void validateResetPasswordWithJwt(ResetPasswordWithJwtDTO resetPasswordWithJwtDTO, String refreshToken){
        final String newPassword = resetPasswordWithJwtDTO.getNewPassword();
        final String username = resetPasswordWithJwtDTO.getUsername();

        util.throwExceptionIfNull(newPassword, "null newPassword");
        util.throwExceptionIfNull(refreshToken, "null refreshToken");
        util.throwExceptionIfNull(username, "null username");

        boolean isValidPassword = util.validatePassword(newPassword);

        if(!isValidPassword) {throw new BaseException("invalid password", HttpStatus.BAD_REQUEST); }

    }

}
