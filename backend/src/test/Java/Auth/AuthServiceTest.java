package Auth;

import Main.DTO.Auth.ResetPasswordDTO;
import Main.Entity.Account;
import Main.Repository.AccountRepository;
import Main.Service.AuthService;
import Account.AccountServiceTestUtil;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.times;

public class AuthServiceTest {
    @Mock
    AccountRepository repository;

    @Mock
    PasswordEncoder passwordEncoder;

    @InjectMocks
    AuthService service;

    AccountServiceTestUtil serviceTestUtil = new AccountServiceTestUtil();


    @Test
    public void testRegister(){
        System.out.println("Running testAdd...");
        List<Account> testCases = serviceTestUtil.getTestCase();

        for(int i = 0 ;i< testCases.size(); i++){
            Account input = testCases.get(i);
            Account expected = new Account();
            expected.setId(i + 1);


            /// passwordEncoder called --> return current password (fake encode)
            when(passwordEncoder.encode(anyString())).thenAnswer(invocation -> invocation.getArgument(0));
            ///  if repo called --> return excepted --> id != default(0)
            when(repository.save(any(Account.class))).thenReturn(expected);

            Account result = service.register(input);

            /// default id= 0 , if repo works --> different id
            assertNotEquals(input.getId(), result.getId(), "#testCase " + (i + 1) + " failed: ID");
            System.out.println("#testCase " + (i + 1) + " passed: account ID " + result.getId());

            verify(repository, times(1)).save(input);
        }
        System.out.println("add passed \n");
    }

    @Test
    public void testResetPassword() {
        System.out.println("Running testResetPassword...");
        List<Account> testCases = serviceTestUtil.getTestCase();

        for(int i = 0; i < testCases.size(); i++) {
            Account input = testCases.get(i);
            String resetToken = "resetToken";
            String newPassword = "newPassword";
            String email = input.getEmail();
            ResetPasswordDTO request = new ResetPasswordDTO(email,resetToken, newPassword);
            Account expected = new Account();
            expected.setId(100 + i);

            ///  passwordEncoder called --> return current password (mock encode)
            when(passwordEncoder.encode(anyString())).thenAnswer(invocation -> invocation.getArgument(0));
            /// repo called --> return expected
            when(repository.save(any())).thenReturn(expected);

            //if repo called --> result = expected
            Account result = service.resetPassword(request);

            assertEquals(expected, result, "#testCase " + (i + 1) + " failed");
            System.out.println("#testCase " + (i + 1) + " passed");

            verify(repository, times(1)).save(any());
        }
        System.out.println("reset Password passed \n");

    }
}
