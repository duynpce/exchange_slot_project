package Main.Service;


<<<<<<< HEAD
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Service;
import java.util.Optional;

import Main.Model.Enity.Account;
import Main.Repository.AccountRepository;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestParam;

@Service
=======
import Main.Config.Security.UserDetailConfig;
import Main.Config.Security.UserDetailServiceConfig;
import Main.DTO.Account.UpdateAccountDTO;
import Main.DTO.Auth.*;
import Main.Enum.Constant;
import Main.Exception.BaseException;
import Main.Utility.jwtUtil;
import Main.Utility.util;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.context.ApplicationContext;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import Main.Entity.Account;
import Main.Repository.AccountRepository;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
>>>>>>> develop
public class AccountService {

    @Autowired
    AccountRepository accountRepository;

<<<<<<< HEAD
    public boolean register(Account account){
        Optional<Account> checkUserName = accountRepository.findByUserName(account.getUserName());
        Optional<Account> checkPhoneNumber = accountRepository.findByPhoneNumber(account.getPhoneNumber());
        if(checkUserName.isPresent() || checkPhoneNumber.isPresent()) return false;

        accountRepository.save(account);
        return true;
    }

    public  boolean login(Account account){
        Optional<Account> optionalAccount = accountRepository.findByUserName(account.getUserName());
        if(optionalAccount.isPresent()){;
            Account FoundAccount = optionalAccount.get();
            return FoundAccount.getPassword().equals(account.getPassword());
        }
        return  false;
    }

    public boolean resetPassword(Account account , String newPassword){
        return accountRepository.resetPassword(account.getUserName(),newPassword) == 1;
    }

=======
    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    ApplicationContext context;

    @Autowired
    jwtUtil jwtUtility;

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
            String refreshToken = jwtUtility.getRefreshToken(user);
            String accessToken = jwtUtility.getAccessToken(user);
            loginResponseDTO =  new LoginResponseDTO(refreshToken,accessToken ,"login success");
            return loginResponseDTO;
        }

        throw new BaseException("incorrect password" , HttpStatus.UNAUTHORIZED);

    }

    public int resetPassword(ResetPasswordDTO resetPasswordDTO, String username){

        String encryptedPassword = passwordEncoder.encode(resetPasswordDTO.getNewPassword());
        return accountRepository.resetPassword(username, encryptedPassword);
    }

    public ResponseRefreshTokenDTO refreshAccessToken(RefreshAccessTokenDTO refreshAccessTokenDTO){
        final String refreshToken = refreshAccessTokenDTO.getRefreshToken();
        final String refreshSecretKey = jwtUtility.getRefreshSecretKey();
        String username =  jwtUtility.extractUsername(refreshToken, refreshSecretKey);

        if(username != null){
            UserDetailConfig user = new
                    UserDetailConfig(context.getBean(UserDetailServiceConfig.class).loadUserByUsername(username));
            boolean validToken = jwtUtility.validateToken(refreshToken, user, refreshSecretKey);

            if(validToken){
                final String accessToken = jwtUtility.getAccessToken(user);
                return new ResponseRefreshTokenDTO(accessToken);
            }
        }

        throw new BaseException("invalid refresh token or access token haven't expired", HttpStatus.UNAUTHORIZED);
    }

    @Caching(put = {
            @CachePut(value = "accountData", key = "#account.username"),
            @CachePut(value = "accountData", key = "#account.studentCode"),
            @CachePut(value = "accountData", key = "#account.phoneNumber")
    })
    public Account  update(Account account ){
        return  accountRepository.save(account);
    }

    @Cacheable(value = "accountData", key = "#username")
    public Account findByUserName(String username) {
        return accountRepository.findByUsername(username).orElseThrow
                (() -> new BaseException("not found account with username : " + username, HttpStatus.NOT_FOUND));
    }

    @Cacheable(value = "accountData" , key = "#studentCode")
    public Account findByStudentCode(String studentCode){
        return  accountRepository.findByStudentCode(studentCode).orElseThrow
                (() -> new BaseException("not found account with studentCode : " + studentCode, HttpStatus.NOT_FOUND));
    }

    @Cacheable(value = "accountExists", key = "#studentCode")
    public boolean existsByStudentCode(String studentCode){
        return accountRepository.existsByStudentCode(studentCode);
    }

    @Cacheable(value = "accountExists", key = "#username")
    public boolean existsByUsername(String username){
        return accountRepository.existsByUsername(username);
    }

    @Cacheable(value = "accountExists", key = "#accountName")
    public boolean existsByAccountName(String accountName){
        return accountRepository.existsByAccountName(accountName);
    }

    @Cacheable(value = "accountExists", key = "#phoneNumber")
    public boolean existsByPhoneNumber(String phoneNumber){
        return accountRepository.existsByPhoneNumber(phoneNumber);
    }
>>>>>>> develop



}
