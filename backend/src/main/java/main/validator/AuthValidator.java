package main.validator;

import main.dto.Auth.ForgetPasswordDTO;
import main.dto.Auth.LoginRequestDTO;
import main.dto.Auth.ResetPasswordWithOtpDTO;
import main.dto.Auth.ResetPasswordWithJwtDTO;
import main.constant.Role;
import main.exception.BaseException;
import main.entity.Account;
import main.service.AccountService;
import main.service.MajorClassService;
import main.utility.Util;

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

        util.throwExceptionIfExists(accountService.existsByUsername(account.getUsername()), "existed username");
        util.throwExceptionIfExists(accountService.existsByPhoneNumber(account.getPhoneNumber()), "existed phone number");
        util.throwExceptionIfExists(accountService.existsByStudentCode(account.getStudentCode()), "existed student code");
        util.throwExceptionIfExists(accountService.existsByAccountName(account.getAccountName()), "existed account name");
        util.throwExceptionIfExists(accountService.existsByEmail(account.getEmail()), "existed email");

        account.setRole(Role.USER);

        util.throwExceptionIfNotExists(majorClassService.existsByClassCode(account.getClassCode()),
                "no class with code: " + account.getClassCode() );
        boolean isValidPassword = util.validatePassword(account.getPassword());

        if(!isValidPassword) {throw new BaseException("invalid password", HttpStatus.BAD_REQUEST); }
    }

    public void validateLogin(LoginRequestDTO loginRequest){

        final String username = loginRequest.getUsername();
        final String password = loginRequest.getPassword();;

        util.throwExceptionIfNotExists(accountService.existsByUsername(username),"no account with username " + username);
    }

    //return email of account to confirm which email to send otp
    public String validateForgetPassword(ForgetPasswordDTO forgetPasswordDTO){

        Account account;

        final String usernameOrEmail = forgetPasswordDTO.getUsernameOrEmail();
        if(util.isEmail(usernameOrEmail)){
           account =  accountService.findByEmail(usernameOrEmail);

        }else {
            account = accountService.findByUsername(usernameOrEmail);
        }


        return account.getEmail();

    }

    public void validateResetPasswordWithOtp(ResetPasswordWithOtpDTO resetPasswordWithOtpDTO){
        final String newPassword = resetPasswordWithOtpDTO.getNewPassword();
        final String email = resetPasswordWithOtpDTO.getEmail();

        boolean isValidPassword = util.validatePassword(newPassword);

        if(!isValidPassword) {throw new BaseException("invalid password", HttpStatus.BAD_REQUEST); }

        util.throwExceptionIfNotExists(accountService.existsByEmail(email),"no account with email: " + email);
    }

    public void validateResetPasswordWithJwt(ResetPasswordWithJwtDTO resetPasswordWithJwtDTO){
        final String newPassword = resetPasswordWithJwtDTO.getNewPassword();
        final String username = resetPasswordWithJwtDTO.getUsername();

        boolean isValidPassword = util.validatePassword(newPassword);

        if(!isValidPassword) {throw new BaseException("invalid password", HttpStatus.BAD_REQUEST); }

    }

}
