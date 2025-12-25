package test.Account;


import main.entity.Account;
import main.repository.AccountRepository;
import main.service.AccountService;
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
public class AccountServiceUnitTest {

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
    public void testFindByStudentCode() {
        System.out.println("Running testFindByStudentCode...");
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
    public void testfindByUsername() {
        System.out.println("Running testfindByUsername...");
        List<Account> testCases = serviceTestUtil.getTestCase();

        for (int i = 0; i < testCases.size(); i++) {
            Account expected = testCases.get(i);
            expected.setId(i + 1 );

            when(repository.findByUsername(expected.getUsername())).thenReturn(Optional.of(expected));

            Account result = service.findByUsername(expected.getUsername());

            assertNotNull(result, "#testCase " + (i + 1) + " failed: result is null");
            assertEquals(expected.getUsername(), result.getUsername(), "#testCase " + (i + 1) + " failed: username mismatch");
            assertEquals(expected.getPhoneNumber(), result.getPhoneNumber(), "#testCase " + (i + 1) + " failed: phone mismatch");

            System.out.println("#testCase " + (i + 1) + " passed: found account " + result.getUsername());
            verify(repository, times(1)).findByUsername(expected.getUsername());
        }
        System.out.println("find by username passed \n");
    }

    @Test
    public void testFindByEmail() {
        System.out.println("Running testFindByEmail...");
        List<Account> testCases = serviceTestUtil.getTestCase();

        for (int i = 0; i < testCases.size(); i++) {
            Account expected = testCases.get(i);
            expected.setId(i + 1);

            when(repository.findByEmail(expected.getEmail())).thenReturn(Optional.of(expected));

            Account result = service.findByEmail(expected.getEmail());

            assertNotNull(result, "#testCase " + (i + 1) + " failed: result is null");
            assertEquals(expected.getEmail(), result.getEmail(), "#testCase " + (i + 1) + " failed: email mismatch");
            assertEquals(expected.getId(), result.getId(), "#testCase " + (i + 1) + " failed: ID mismatch");

            System.out.println("#testCase " + (i + 1) + " passed: found account by email " + result.getEmail());
            verify(repository, times(1)).findByEmail(expected.getEmail());
        }
        System.out.println("find by email passed \n");
    }

    @Test
    public void testExistsByStudentCode() {
        System.out.println("Running testExistsByStudentCode...");
        List<Account> testCases = serviceTestUtil.getTestCase();

        for (int i = 0; i < testCases.size(); i++) {
            Account expected = testCases.get(i);
            expected.setId(i + 1);

            when(repository.existsByStudentCode(expected.getStudentCode())).thenReturn(true);

            boolean result = service.existsByStudentCode(expected.getStudentCode());

            assertTrue(result, "#testCase " + (i + 1) + " failed: expected true");
            System.out.println("#testCase " + (i + 1) + " passed: exists studentCode " + expected.getStudentCode());
            verify(repository, times(1)).existsByStudentCode(expected.getStudentCode());
        }
        System.out.println("exists by student code passed \n");
    }

    @Test
    public void testExistsByUsername() {
        System.out.println("Running testExistsByUsername...");
        List<Account> testCases = serviceTestUtil.getTestCase();

        for (int i = 0; i < testCases.size(); i++) {
            Account expected = testCases.get(i);
            expected.setId(i + 1);

            when(repository.existsByUsername(expected.getUsername())).thenReturn(true);

            boolean result = service.existsByUsername(expected.getUsername());

            assertTrue(result, "#testCase " + (i + 1) + " failed: expected true");
            System.out.println("#testCase " + (i + 1) + " passed: exists username " + expected.getUsername());
            verify(repository, times(1)).existsByUsername(expected.getUsername());
        }
        System.out.println("exists by username passed \n");
    }

    @Test
    public void testExistsByAccountName() {
        System.out.println("Running testExistsByAccountName...");
        List<Account> testCases = serviceTestUtil.getTestCase();

        for (int i = 0; i < testCases.size(); i++) {
            Account expected = testCases.get(i);
            expected.setId(i + 1);

            when(repository.existsByAccountName(expected.getAccountName())).thenReturn(true);

            boolean result = service.existsByAccountName(expected.getAccountName());

            assertTrue(result, "#testCase " + (i + 1) + " failed: expected true");
            System.out.println("#testCase " + (i + 1) + " passed: exists accountName " + expected.getAccountName());
            verify(repository, times(1)).existsByAccountName(expected.getAccountName());
        }
        System.out.println("exists by account name passed \n");
    }

    @Test
    public void testExistsByPhoneNumber() {
        System.out.println("Running testExistsByPhoneNumber...");
        List<Account> testCases = serviceTestUtil.getTestCase();

        for (int i = 0; i < testCases.size(); i++) {
            Account expected = testCases.get(i);
            expected.setId(i + 1);

            when(repository.existsByPhoneNumber(expected.getPhoneNumber())).thenReturn(true);

            boolean result = service.existsByPhoneNumber(expected.getPhoneNumber());

            assertTrue(result, "#testCase " + (i + 1) + " failed: expected true");
            System.out.println("#testCase " + (i + 1) + " passed: exists phoneNumber " + expected.getPhoneNumber());
            verify(repository, times(1)).existsByPhoneNumber(expected.getPhoneNumber());
        }
        System.out.println("exists by phone number passed \n");
    }

    @Test
    public void testExistsById() {
        System.out.println("Running testExistsById...");
        List<Account> testCases = serviceTestUtil.getTestCase();

        for (int i = 0; i < testCases.size(); i++) {
            Account expected = testCases.get(i);
            expected.setId(i + 1);

            when(repository.existsById(expected.getId())).thenReturn(true);

            boolean result = service.existsById(expected.getId());

            assertTrue(result, "#testCase " + (i + 1) + " failed: expected true");
            System.out.println("#testCase " + (i + 1) + " passed: exists id " + expected.getId());
            verify(repository, times(1)).existsById(expected.getId());
        }
        System.out.println("exists by id passed \n");
    }

    @Test
    public void testExistsByEmail() {
        System.out.println("Running testExistsByEmail...");
        List<Account> testCases = serviceTestUtil.getTestCase();

        for (int i = 0; i < testCases.size(); i++) {
            Account expected = testCases.get(i);
            expected.setId(i + 1);

            when(repository.existsByEmail(expected.getEmail())).thenReturn(true);

            boolean result = service.existsByEmail(expected.getEmail());

            assertTrue(result, "#testCase " + (i + 1) + " failed: expected true");
            System.out.println("#testCase " + (i + 1) + " passed: exists email " + expected.getEmail());
            verify(repository, times(1)).existsByEmail(expected.getEmail());
        }
        System.out.println("exists by email passed \n");
    }


}
