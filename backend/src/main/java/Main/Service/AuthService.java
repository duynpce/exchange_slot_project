package Main.Service;

import Main.Config.Security.UserDetailConfig;
import Main.Config.Security.UserDetailServiceConfig;
import Main.DTO.Auth.*;
import Main.Entity.Account;
import Main.Exception.BaseException;
import Main.Repository.AccountRepository;
import Main.Utility.CacheUtil;
import Main.Utility.JwtUtil;
import Main.Utility.Util;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.context.ApplicationContext;
import org.springframework.http.HttpStatus;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Random;

@RequiredArgsConstructor
@Service
@Transactional
public class AuthService {
    private final AccountService accountService;
    private final PasswordEncoder passwordEncoder;
    private final ApplicationContext context;
    private final JwtUtil jwtUtil;
    private final Util util;
    private final CacheManager cacheManager;

    private final String cacheExists = "accountExists";


    public Account register(Account account){
        String encryptedPassword = passwordEncoder.encode(account.getPassword());
        account.setPassword(encryptedPassword);
        return accountService.save(account);
    }

    public LoginResponseDTO login(LoginRequestDTO loginRequest){
        final String username = loginRequest.getUsername();
        final String password = loginRequest.getPassword();
        Account foundAccount = accountService.findByUsername(username);
        final String encryptedPassword = foundAccount.getPassword();
        boolean correctPassword = passwordEncoder.matches(password,encryptedPassword );

        if(correctPassword) {
            LoginResponseDTO loginResponseDTO;
            UserDetailConfig user = new UserDetailConfig(foundAccount);
            String refreshToken = jwtUtil.getRefreshToken(user);
            String accessToken = jwtUtil.getAccessToken(user);
            loginResponseDTO =  new LoginResponseDTO(refreshToken,accessToken);
            return loginResponseDTO;
        }

        throw new BaseException("incorrect password" , HttpStatus.UNAUTHORIZED);

    }


    public String generateOtp(){
        return String.format("%06d", new Random().nextInt(1000000));
    }

    // Generate and cache OTP for forget password , if OTP already exists in cache, update it
    @CachePut(value = "OTP", key = "#email")
    public OtpDTO forgetPassword(String email){
        return new OtpDTO(generateOtp(), email);

    }

    public  String getOtpFromCache(String email){
        Cache cache = cacheManager.getCache("OTP");
        util.throwExceptionIfNull(cache, "OTP cache not found");

        OtpDTO otpDTO = cache.get(email, OtpDTO.class);
        util.throwExceptionIfNull(otpDTO, "no OTP found for email: " + email);

        return otpDTO.getOtp();
    }

    public String generateResetToken(){
        return String.format("%08d", new Random().nextInt(100000000));
    }

    @Caching(
            evict = {@CacheEvict(value = "OTP", key = "#otpDTO.email")}, /// delete OTP after verified
            put   = { @CachePut(value = "ResetToken", key = "#otpDTO.email") }
    )
    public ResetTokenDTO verifyOtp(OtpDTO otpDTO){
        final String email = otpDTO.getEmail();
        final String inputOtp = otpDTO.getOtp();

        final String cachedOtp = getOtpFromCache(email);

        util.throwExceptionIfNotEquals(inputOtp, cachedOtp, "invalid or expired OTP");

        return new ResetTokenDTO(email, generateResetToken());
    }

    public String getResetTokenFromCache(String email){
        Cache cache = cacheManager.getCache("ResetToken");
        util.throwExceptionIfNull(cache, "ResetToken cache not found");

        ResetTokenDTO resetTokenDTO = cache.get(email, ResetTokenDTO.class);
        util.throwExceptionIfNull(resetTokenDTO, "no ResetToken found for email: " + email);

        return resetTokenDTO.getResetToken();
    }

    @CacheEvict(value = "ResetToken", key = "#resetPasswordDTO.email") // delete reset token after used
    public void resetPasswordWithOtp(ResetPasswordDTO resetPasswordDTO){
        final String email = resetPasswordDTO.getEmail();
        final String resetToken = resetPasswordDTO.getResetToken();
        final String newPassword = resetPasswordDTO.getNewPassword();

        final String cachedResetToken = getResetTokenFromCache(email);

        util.throwExceptionIfNotEquals(resetToken, cachedResetToken, "invalid reset token");

        String encryptedPassword = passwordEncoder.encode(newPassword);

        Account accountWithNewPassword = accountService.findByEmail(email);
        accountWithNewPassword.setPassword(encryptedPassword);

        accountService.save(accountWithNewPassword);

    }

    public void resetPasswordWithJwt(ResetPasswordWithJwtDTO resetPasswordWithJwtDTO){
        final String newPassword = resetPasswordWithJwtDTO.getNewPassword();
        final String enteredUsername = resetPasswordWithJwtDTO.getUsername();
        final String refreshToken = resetPasswordWithJwtDTO.getRefreshToken();


        final String refreshSecretKey = jwtUtil.getRefreshSecretKey();
        UserDetailConfig user = new
                UserDetailConfig(context.getBean(UserDetailServiceConfig.class).loadUserByUsername(enteredUsername));
        boolean validToken = jwtUtil.validateToken(refreshToken, user, refreshSecretKey);

        if(!validToken){throw new BaseException("invalid or expired refresh token", HttpStatus.UNAUTHORIZED);}

        final String jwtUsername =  jwtUtil.extractUsername(refreshToken, refreshSecretKey);

        util.throwExceptionIfNotEquals(enteredUsername,jwtUsername , "username in request does not match username in token");

        Account account = accountService.findByUsername(jwtUsername);

        String encryptedPassword = passwordEncoder.encode(newPassword);
        account.setPassword(encryptedPassword);

        accountService.save(account);
    }


    public AccessTokenDTO refreshAccessToken(String refreshToken){
        final String refreshSecretKey = jwtUtil.getRefreshSecretKey();
        String username =  jwtUtil.extractUsername(refreshToken, refreshSecretKey);

        UserDetailConfig user = new
                UserDetailConfig(context.getBean(UserDetailServiceConfig.class).loadUserByUsername(username));
        boolean validToken = jwtUtil.validateToken(refreshToken, user, refreshSecretKey);

        if(validToken){
            final String accessToken = jwtUtil.getAccessToken(user);
            return new AccessTokenDTO(accessToken);
        }

        throw new BaseException("invalid refresh token", HttpStatus.UNAUTHORIZED);
    }
}
