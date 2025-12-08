package Main.Service;

import Main.Config.Security.UserDetailConfig;
import Main.Config.Security.UserDetailServiceConfig;
import Main.DTO.Auth.*;
import Main.Entity.Account;
import Main.Exception.BaseException;
import Main.Repository.AccountRepository;
import Main.Utility.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Caching;
import org.springframework.context.ApplicationContext;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
@Transactional
public class AuthService {
    private final AccountRepository accountRepository;
    private final PasswordEncoder passwordEncoder;
    private final ApplicationContext context;
    private final JwtUtil jwtUtil;

    @Caching(evict = { ///  if  registered success --> delete accountExists of the account
            @CacheEvict(value = "accountExists", key = "#account.studentCode"),
            @CacheEvict(value = "accountExists", key = "#account.username"),
            @CacheEvict(value = "accountExists", key = "#account.accountName"),
            @CacheEvict(value = "accountExists", key = "#account.phoneNumber")
    })
    public Account register(Account account){
        String encryptedPassword = passwordEncoder.encode(account.getPassword());
        account.setPassword(encryptedPassword);
        return accountRepository.save(account);
    }

    public LoginResponseDTO login(LoginRequestDTO loginRequest){
        final String username = loginRequest.getUsername();
        final String password = loginRequest.getPassword();
        Account foundAccount = accountRepository.findByUsername(username)
                .orElseThrow(() -> new BaseException("Account not found", HttpStatus.UNAUTHORIZED));
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

    public int resetPassword(ResetPasswordDTO resetPasswordDTO, String username){

        String encryptedPassword = passwordEncoder.encode(resetPasswordDTO.getNewPassword());
        return accountRepository.resetPassword(username, encryptedPassword);
    }

    public AccessTokenDTO refreshAccessToken(String refreshToken){
        final String refreshSecretKey = jwtUtil.getRefreshSecretKey();
        String username =  jwtUtil.extractUsername(refreshToken, refreshSecretKey);

        if(username != null){
            UserDetailConfig user = new
                    UserDetailConfig(context.getBean(UserDetailServiceConfig.class).loadUserByUsername(username));
            boolean validToken = jwtUtil.validateToken(refreshToken, user, refreshSecretKey);

            if(validToken){
                final String accessToken = jwtUtil.getAccessToken(user);
                return new AccessTokenDTO(accessToken);
            }
        }

        throw new BaseException("invalid refresh token or access token haven't expired", HttpStatus.UNAUTHORIZED);
    }
}
