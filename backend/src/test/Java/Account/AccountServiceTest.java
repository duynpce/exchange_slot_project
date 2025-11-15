package Account;


import Main.DTO.Auth.ResetPasswordDTO;
import Main.Entity.Account;
import Main.Repository.AccountRepository;
import Main.Service.AccountService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import java.util.List;
import java.util.Optional;

@ExtendWith(MockitoExtension.class)
public class AccountServiceTest {

    @Mock
    AccountRepository repository;

    @Mock
    PasswordEncoder passwordEncoder;

    @InjectMocks
    AccountService service;

    AccountServiceTestUtil serviceTestUtil = new AccountServiceTestUtil();

//    @BeforeEach
//    public void init() {
//
//    }

    /// reset after each test --> for test the whole class
    @AfterEach
    public void tearDown() {
        reset(repository);
    }

    @Test
    public void testAdd(){
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
        List<Account> testCases = serviceTestUtil.getTestCase();

        for(int i = 0; i < testCases.size(); i++) {
            Account input = testCases.get(i);
            String username = input.getUsername();
            String newPassword = "newPassword";
            ResetPasswordDTO request = new ResetPasswordDTO(newPassword);
            int expected = 1;

            ///  passwordEncoder called --> return current password (mock encode)
            when(passwordEncoder.encode(anyString())).thenAnswer(invocation -> invocation.getArgument(0));
            /// repo called --> return expected
            when(repository.resetPassword(anyString(), anyString())).thenReturn(expected);

            //if repo called --> result = expected
            int result = service.resetPassword(request, username);

            assertEquals(expected, result, "#testCase " + (i + 1) + " failed");
            System.out.println("#testCase " + (i + 1) + " passed");

            verify(repository, times(1)).resetPassword(username, newPassword);
        }
        System.out.println("reset Password passed \n");

    }


    @Test
    public void testFindByStudentCode() {
        List<Account> testCases = serviceTestUtil.getTestCase();

        for (int i = 0; i < testCases.size(); i++) {
            Account expected = testCases.get(i);
            expected.setId(i + 1 );

            when(repository.findByStudentCode(expected.getStudentCode())).thenReturn(Optional.of(expected));

            Account result = service.findByStudentCode(expected.getStudentCode());

            assertNotNull(result, "#testCase " + (i + 1) + " failed: result is null");
            assertEquals(expected.getUsername(), result.getUsername(), "#testCase " + (i + 1) + " failed: username mismatch");
            assertEquals(expected.getId(), result.getId(), "#testCase " + (i + 1) + " failed: ID mismatch");

            System.out.println("#testCase " + (i + 1) + " passed: found account " + result.getUsername());
            verify(repository, times(1)).findByStudentCode(expected.getStudentCode());
        }
        System.out.println("find by student code passed \n");
    }

    @Test
    public void testFindByUsername() {
        List<Account> testCases = serviceTestUtil.getTestCase();

        for (int i = 0; i < testCases.size(); i++) {
            Account expected = testCases.get(i);
            expected.setId(i + 1 );

            when(repository.findByUsername(expected.getUsername())).thenReturn(Optional.of(expected));

            Account result = service.findByUserName(expected.getUsername());

            assertNotNull(result, "#testCase " + (i + 1) + " failed: result is null");
            assertEquals(expected.getUsername(), result.getUsername(), "#testCase " + (i + 1) + " failed: username mismatch");
            assertEquals(expected.getPhoneNumber(), result.getPhoneNumber(), "#testCase " + (i + 1) + " failed: phone mismatch");

            System.out.println("#testCase " + (i + 1) + " passed: found account " + result.getUsername());
            verify(repository, times(1)).findByUsername(expected.getUsername());
        }
        System.out.println("find by username passed \n");
    }


}
