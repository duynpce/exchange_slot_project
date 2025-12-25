package test.Auth;

import test.Account.AccountServiceTestUtil;
import test.IntegrationTest;
import main.config.security.UserDetailConfig;
import main.config.security.UserDetailServiceConfig;
import main.dto.Auth.*;
import main.entity.Account;
import main.entity.MajorClass;
import main.exception.BaseException;
import main.service.AccountService;
import main.service.AuthService;
import main.service.MajorClassService;
import main.utility.JwtUtil;
import main.utility.Util;
import main.validator.AuthValidator;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.bean.override.mockito.MockitoBean;

import java.util.List;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

public class AuthIT extends IntegrationTest {

    @Autowired
    AuthService authService;

    @Autowired
    AuthValidator authValidator;

    @Autowired
    Util util;

    @Autowired
    AccountService accountService;

    @Autowired
    MajorClassService majorClassService;

    @MockitoBean
    JwtUtil jwtUtil;

    @MockitoBean
    UserDetailServiceConfig userDetailsServiceConfig;

    @MockitoBean
    PasswordEncoder passwordEncoder;

    // Mocked authorities for UserDetails
    List<GrantedAuthority> authorities =
            AuthorityUtils.createAuthorityList("ROLE_ADMIN", "ROLE_USER");


    static AccountServiceTestUtil accountServiceTestUtil = new AccountServiceTestUtil();

    static Stream<Account> testCasesProvider() {
        return accountServiceTestUtil.getTestCase().stream();
    }

    @ParameterizedTest
    @MethodSource("testCasesProvider")
    public void testRegister_Success(Account account) {
        // Add major class to db

        when(passwordEncoder.encode(account.getPassword())).thenReturn(account.getPassword());
        majorClassService.save(new MajorClass(0, account.getClassCode(), "1,2"));
        authValidator.validateRegister(account);
        Account registered = authService.register(account);

        assertTrue(registered.getId() > 0);
        assertEquals(account.getUsername(), registered.getUsername());
        assertEquals(account.getEmail(), registered.getEmail());

        System.out.println("Register test passed.");
    }

    @ParameterizedTest
    @MethodSource("testCasesProvider")
    public void testRegister_DuplicateUsername(Account account) {
        // Add major class to db
        majorClassService.save(new MajorClass(0, account.getClassCode(), "1,2"));

        // Register once
        when(passwordEncoder.encode(account.getPassword())).thenReturn(account.getPassword());
        authValidator.validateRegister(account);
        authService.register(account);

        // Try to register again with same username
        Account duplicateAccount = new Account();
        duplicateAccount.setUsername(account.getUsername());
        duplicateAccount.setPassword("NewPassword123!");
        duplicateAccount.setEmail("newemail@example.com");
        duplicateAccount.setPhoneNumber("0987654321");
        duplicateAccount.setStudentCode("NEW123");
        duplicateAccount.setAccountName("New Name");
        duplicateAccount.setClassCode(account.getClassCode());

        Exception exception = assertThrows(BaseException.class, () -> {
            authValidator.validateRegister(duplicateAccount);
        });

        String expectedMessage = "existed username";
        String actualMessage = exception.getMessage();
        assertEquals(expectedMessage, actualMessage);

        System.out.println("Register with duplicate username test passed.");
    }

    @ParameterizedTest
    @MethodSource("testCasesProvider")
    public void testLogin_Success(Account account) {
        // set up
        when(passwordEncoder.encode(account.getPassword())).thenReturn(account.getPassword());
        majorClassService.save(new MajorClass(0, account.getClassCode(), "1,2"));
        authValidator.validateRegister(account);
        authService.register(account);

        LoginRequestDTO loginRequest = new LoginRequestDTO();
        loginRequest.setUsername(account.getUsername());
        loginRequest.setPassword(account.getPassword());

        // mock correct password
        when(passwordEncoder.matches(anyString(), anyString())).thenReturn(true);

        authValidator.validateLogin(loginRequest);
        LoginResponseDTO response = authService.login(loginRequest);

        //not assert null because the token is generated by jwtUtil, which is mocked

        System.out.println("Login test passed.");
    }

    @ParameterizedTest
    @MethodSource("testCasesProvider")
    public void testLogin_InvalidUsername(Account account) {
        LoginRequestDTO loginRequest = new LoginRequestDTO();
        loginRequest.setUsername("nonexistent_user");
        loginRequest.setPassword("SomePassword123!");

        Exception exception = assertThrows(BaseException.class, () -> authValidator.validateLogin(loginRequest));

        String expectedMessage = "no account with username nonexistent_user";
        String actualMessage = exception.getMessage();
        assertEquals(expectedMessage, actualMessage);

        System.out.println("Login with invalid username test passed.");
    }

    @ParameterizedTest
    @MethodSource("testCasesProvider")
    public void testForgetPassword_Success(Account account) {
        // set up
        when(passwordEncoder.encode(account.getPassword())).thenReturn(account.getPassword());
        majorClassService.save(new MajorClass(0, account.getClassCode(), "1,2"));
        authValidator.validateRegister(account);
        authService.register(account);


        ForgetPasswordDTO forgetPasswordDTO = new ForgetPasswordDTO();
        forgetPasswordDTO.setUsernameOrEmail(account.getEmail());

        String email = authValidator.validateForgetPassword(forgetPasswordDTO);
        assertEquals(account.getEmail(), email);

        OtpDTO otpDTO = authService.forgetPassword(email);
        assertNotNull(otpDTO);
        assertNotNull(otpDTO.getOtp());

        System.out.println("Forget password test passed.");
    }

    @ParameterizedTest
    @MethodSource("testCasesProvider")
    public void testForgetPassword_InvalidEmail(Account account) {
        ForgetPasswordDTO forgetPasswordDTO = new ForgetPasswordDTO();
        forgetPasswordDTO.setUsernameOrEmail("nonexistent@email.com");

        Exception exception = assertThrows(BaseException.class, () ->
                authValidator.validateForgetPassword(forgetPasswordDTO));

        assertNotNull(exception);

        System.out.println("Forget password with invalid email test passed.");
    }

    @ParameterizedTest
    @MethodSource("testCasesProvider")
    public void testResetPasswordWithOtp_Success(Account account) {
        // Add major class and register account
        when(passwordEncoder.encode(account.getPassword())).thenReturn(account.getPassword());
        majorClassService.save(new MajorClass(0, account.getClassCode(), "1,2"));
        authValidator.validateRegister(account);
        authService.register(account);

        // Generate OTP
        OtpDTO otpDTO = authService.forgetPassword(account.getEmail());

        // Verify OTP
        ResetTokenDTO resetTokenDTO = authService.verifyOtp(otpDTO);
        assertNotNull(resetTokenDTO);

        // Reset password
        ResetPasswordDTO resetPasswordDTO = new ResetPasswordDTO();
        resetPasswordDTO.setEmail(account.getEmail());
        resetPasswordDTO.setNewPassword("NewPassword123!");
        resetPasswordDTO.setResetToken(resetTokenDTO.getResetToken());

        authValidator.validateResetPasswordWithOtp(resetPasswordDTO);
        authService.resetPasswordWithOtp(resetPasswordDTO);

        System.out.println("Reset password with OTP test passed.");
    }

    @ParameterizedTest
    @MethodSource("testCasesProvider")
    public void testResetPasswordWithOtp_InvalidPassword(Account account) {
        // Add major class and register account
        when(passwordEncoder.encode(account.getPassword())).thenReturn(account.getPassword());
        majorClassService.save(new MajorClass(0, account.getClassCode(), "1,2"));
        authValidator.validateRegister(account);
        authService.register(account);

        ResetPasswordDTO resetPasswordDTO = new ResetPasswordDTO();
        resetPasswordDTO.setEmail(account.getEmail());
        resetPasswordDTO.setNewPassword("weak"); // Invalid password
        resetPasswordDTO.setResetToken("some_token");

        Exception exception = assertThrows(BaseException.class, () -> {
            authValidator.validateResetPasswordWithOtp(resetPasswordDTO);
        });

        String expectedMessage = "invalid password";
        String actualMessage = exception.getMessage();
        assertEquals(expectedMessage, actualMessage);

        System.out.println("Reset password with invalid password test passed.");
    }

    @Test
    public void testResetPasswordWithJwt_Success() {
        Account account = accountServiceTestUtil.getTestCase().getFirst();
        // set up
        when(passwordEncoder.encode(account.getPassword())).thenReturn(account.getPassword());
        majorClassService.save(new MajorClass(0, account.getClassCode(), "1,2"));
        authValidator.validateRegister(account);
        authService.register(account);

        UserDetails userDetails = new User(account.getUsername(), account.getPassword(), authorities);
        final String oldPassword = account.getPassword();
        final String refreshToken = "valid-refresh-token";
        final String refreshSecretKey = "refresh-secret-key";
        final String newPassword = "NewPassword123!";


        // Mock JWTUtil to return the account's username
        when(jwtUtil.getRefreshSecretKey()).thenReturn(refreshSecretKey);
        when(userDetailsServiceConfig.loadUserByUsername(account.getUsername())).thenReturn(userDetails);
        when(jwtUtil.validateToken(anyString(), any(UserDetailConfig.class), anyString())).thenReturn(true);
        when(jwtUtil.extractUsername(refreshToken, refreshSecretKey)).thenReturn(account.getUsername());
        when(passwordEncoder.encode(newPassword)).thenReturn(newPassword);


        ResetPasswordWithJwtDTO resetPasswordWithJwtDTO = new ResetPasswordWithJwtDTO();
        resetPasswordWithJwtDTO.setUsername(account.getUsername());
        resetPasswordWithJwtDTO.setNewPassword(newPassword);

        authValidator.validateResetPasswordWithJwt(resetPasswordWithJwtDTO);
        authService.resetPasswordWithJwt(resetPasswordWithJwtDTO, refreshToken);

        Account updatedAccount = accountService.findByUsername(account.getUsername());
        assertNotNull(updatedAccount);
        assertNotEquals(oldPassword, updatedAccount.getPassword(), "Password should be updated");
        assertEquals(newPassword, updatedAccount.getPassword(), "Password should be encrypted");

        System.out.println("Reset password with JWT test passed.");
    }

    @ParameterizedTest
    @MethodSource("testCasesProvider")
    public void RefreshAccessToken_Success(Account account) {

        // set up
        final String refreshToken = "valid-refresh-token";
        final String refreshSecretKey = "refresh-secret-key";
        final UserDetails userDetails = new User(account.getUsername(), account.getPassword(), authorities);
        when(passwordEncoder.encode(account.getPassword())).thenReturn(account.getPassword());
        majorClassService.save(new MajorClass(0, account.getClassCode(), "1,2"));
        authValidator.validateRegister(account);
        authService.register(account);

        when(userDetailsServiceConfig.loadUserByUsername(account.getUsername())).thenReturn(userDetails);
        when(jwtUtil.getRefreshSecretKey()).thenReturn(refreshSecretKey);
        when(jwtUtil.extractUsername(refreshToken, refreshSecretKey)).thenReturn(account.getUsername());
        when(jwtUtil.validateToken(anyString() ,any(UserDetailConfig.class) ,anyString())).thenReturn(true);
        when(jwtUtil.getAccessToken(any(UserDetailConfig.class))).thenReturn("new-access-token");

        AccessTokenDTO accessTokenDTO = authService.refreshAccessToken(refreshToken);

        assertEquals("new-access-token", accessTokenDTO.getAccessToken(), "Access token should match expected value");

        System.out.println("Refresh access token test passed.");
    }

    @Test
    public void RefreshAccessToken_NoRefreshTokenInCookies() {
       String refreshToken = null;

        Exception exception = assertThrows(BaseException.class, () -> authService.refreshAccessToken(refreshToken));

        System.out.println("Refresh access token with no refresh token in cookies test passed.");
    }
}
