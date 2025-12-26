package test.Auth;

import main.config.security.UserDetailServiceConfig;
import main.dto.Auth.*;
import main.entity.Account;
import main.repository.AccountRepository;
import main.service.AccountService;
import main.service.AuthService;
import main.utility.JwtUtil;
import main.utility.Util;
import test.Account.AccountServiceTestUtil;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.context.ApplicationContext;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;

@ExtendWith(MockitoExtension.class)
public class AuthServiceUnitTest {

    @Mock
    AccountService accountService;

    @Mock
    AccountRepository repository;

    @Mock
    PasswordEncoder passwordEncoder;

    @Mock
    ApplicationContext context;

    @Mock
    JwtUtil jwtUtil;

    @Mock
    Util util;

    @Mock
    CacheManager cacheManager;

    @Mock
    Cache cache;

    @Mock
    UserDetailServiceConfig userDetailServiceConfig;

    @InjectMocks
    AuthService service;

    AccountServiceTestUtil serviceTestUtil = new AccountServiceTestUtil();


    @AfterEach
    public void tearDown() {
        reset(accountService, passwordEncoder, context, jwtUtil, util, cacheManager, cache, userDetailServiceConfig);
    }

    @Test
    public void testRegister(){
        System.out.println("Running test register...");
        List<Account> testCases = serviceTestUtil.getTestCase();

        for(int i = 0 ;i< testCases.size(); i++){
            Account input = testCases.get(i);
            Account excepted = new Account();
            //default id = 0, different id if saved
            excepted.setId(i + 1);


            when(passwordEncoder.encode(anyString())).thenReturn(input.getPassword());
            when(accountService.save(input)).thenReturn(excepted);

            Account result = service.register(input);

            // if id is different, means saved successfully
            assertNotEquals(input.getId(), result.getId(), "#testCase " + (i + 1) + " failed: ID");
            System.out.println("#testCase " + (i + 1) + " passed: account ID " + result.getId());

            verify(accountService, times(1)).save(input);
        }
        System.out.println("add passed \n");
    }

    @Test
    public void testResetPasswordWithOtp() {
        System.out.println("Running testResetPasswordWithOtp...");
        List<Account> testCases = serviceTestUtil.getTestCase();

        for(int i = 0; i < testCases.size(); i++) {
            Account input = testCases.get(i);
            String resetToken = "resetToken";
            String newPassword = "newPassword";
            String email = input.getEmail();
            ResetPasswordWithOtpDTO request = new ResetPasswordWithOtpDTO(email,resetToken, newPassword);

            when(cacheManager.getCache("ResetToken")).thenReturn(cache);
            when(cache.get(email, ResetTokenDTO.class)).thenReturn(new ResetTokenDTO(resetToken, email));
            when(passwordEncoder.encode(newPassword)).thenReturn("encryptedNewPassword");
            when(accountService.findByEmail(email)).thenReturn(input);


            service.resetPasswordWithOtp(request);


            System.out.println("#testCase " + (i + 1) + " passed");

            verify(accountService, times(1)).save(input);
        }
        System.out.println("reset Password passed \n");

    }

    @Test
    public void testLogin() {
        System.out.println("Running testLogin...");
        List<Account> testCases = serviceTestUtil.getTestCase();

        for (int i = 0; i < testCases.size(); i++) {
            Account input = testCases.get(i);
            input.setPassword("encryptedPwd");
            String plainPassword = "plainPwd";

            when(accountService.findByUsername(input.getUsername())).thenReturn(input);
            when(passwordEncoder.matches(plainPassword, input.getPassword())).thenReturn(true);
            when(jwtUtil.getRefreshToken(any())).thenReturn("refresh-token-" + i);
            when(jwtUtil.getAccessToken(any())).thenReturn("access-token-" + i);

            LoginRequestDTO req = new LoginRequestDTO(input.getUsername(), plainPassword);
            LoginResponseDTO resp = service.login(req);

            assertNotNull(resp, "#testCase " + (i + 1) + " failed: resp null");
            assertEquals("refresh-token-" + i, resp.getRefreshToken(), "#testCase " + (i + 1) + " failed: refresh token");
            assertEquals("access-token-" + i, resp.getAccessToken(), "#testCase " + (i + 1) + " failed: access token");

            verify(accountService, times(1)).findByUsername(input.getUsername());
        }
        System.out.println("login passed \n");
    }

    @Test
    public void testForgetPassword() {
        System.out.println("Running testGenerateOtp_and_forgetPassword...");
        String email = "user@example.com";

        OtpDTO otpDTO = service.forgetPassword(email);

        assertEquals(email, otpDTO.getEmail());
        assertEquals(6, otpDTO.getOtp().length());

        System.out.println("generateOtp and forgetPassword passed \n");
    }

    @Test
    public void testGetOtpFromCache_and_verifyOtp() {
        System.out.println("Running testGetOtpFromCache_and_verifyOtp...");
        List<Account> testCases = serviceTestUtil.getTestCase();

        for (int i = 0; i < testCases.size(); i++) {
            Account sample = testCases.get(i);
            String email = sample.getEmail();
            String otp = "123456";

            // Mock cache retrieval
            when(cacheManager.getCache("OTP")).thenReturn(cache);
            // util.throwExceptionIfNull should not throw in happy path
            doNothing().when(util).throwExceptionIfNull(any(), anyString());
            when(cache.get(email, OtpDTO.class)).thenReturn(new OtpDTO(otp, email));

            String cachedOtp = service.getOtpFromCache(email);


            // verifyOtp: util.throwExceptionIfNotEquals should not throw in happy path
            doNothing().when(util).throwExceptionIfNotEquals(anyString(), anyString(), anyString());
            // verifyOtp returns a ResetTokenDTO (reset token generated inside method)
            ResetTokenDTO resetTokenDTO = service.verifyOtp(new OtpDTO(otp, email));
            assertEquals(email, resetTokenDTO.getEmail());
            assertEquals(otp, cachedOtp, "#testCase " + (i + 1) + " failed: otp mismatch");

            verify(cacheManager, atLeastOnce()).getCache("OTP");
        }

        System.out.println("getOtpFromCache and verifyOtp passed \n");
    }

    @Test
    public void testGenerateResetToken() {
        System.out.println("Running testGenerateResetToken...");
        String token = service.generateResetToken();
        assertNotNull(token);
        assertTrue(token.matches("\\d{8}"), "Reset token should be 8 digits");
        System.out.println("generateResetToken passed \n");
    }

    @Test
    public void testRefreshAccessToken() {
        System.out.println("Running testRefreshAccessToken...");
        String refreshToken = "some-refresh-token";
        String username = "user1";

        when(jwtUtil.getRefreshSecretKey()).thenReturn("secretKey");
        when(jwtUtil.extractUsername(refreshToken, "secretKey")).thenReturn(username);
        // context.getBean(UserDetailServiceConfig.class).loadUserByUsername(...) -> return some UserDetails
        when(context.getBean(UserDetailServiceConfig.class)).thenReturn(userDetailServiceConfig);
        when(userDetailServiceConfig.loadUserByUsername(username)).thenReturn(mock(UserDetails.class));
        // The service builds a UserDetailConfig and passes it to jwtUtil.validateToken/getAccessToken.
        // We mock jwtUtil to accept any user object.
        when(jwtUtil.validateToken(anyString(), any(), anyString())).thenReturn(true);
        when(jwtUtil.getAccessToken(any())).thenReturn("new-access-token");

        AccessTokenDTO dto = service.refreshAccessToken(refreshToken);

        assertNotNull(dto);
        assertEquals("new-access-token", dto.getAccessToken());

        verify(jwtUtil, times(1)).getAccessToken(any());
        System.out.println("refreshAccessToken passed \n");
    }

//    @Test
//    public void testResetPasswordWithJwt_placeholder() {
//        // This method requires wiring of context.getBean(UserDetailServiceConfig.class) and jwt validation
//        // and AccountService.findByUsername + save interactions. I can implement a detailed test but need
//        // confirmation on how UserDetailServiceConfig.loadUserByUsername should behave in your project.
//        // implement logic where you not sure and need me to implement logic for you
//    }
}
