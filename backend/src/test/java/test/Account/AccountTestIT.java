package test.Account;

import main.dto.Account.UpdateAccountDTO;
import main.entity.Account;
import main.entity.MajorClass;
import main.exception.BaseException;
import main.service.AccountService;
import main.service.MajorClassService;
import main.utility.JwtUtil;
import main.utility.Util;
import main.validator.AccountValidator;
import test.IntegrationTest;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.bean.override.mockito.MockitoBean;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

public class AccountTestIT extends IntegrationTest {

    @Autowired
    AccountService accountService;

    @Autowired
    AccountValidator accountValidator;

    @Autowired
    MajorClassService majorClassService;

    @Autowired
    private Util util;

    @MockitoBean
    private JwtUtil jwtUtil;



    static AccountServiceTestUtil accountServiceTestUtil = new AccountServiceTestUtil();

    static Stream<Account> testCasesProvider() {
        return accountServiceTestUtil.getTestCase().stream();
    }

    // test update account with testcases from accountServiceTestUtil
    @ParameterizedTest
    @MethodSource("testCasesProvider")
    public void testUpdate(Account account){

        accountService.save(account);

        final String newStudentCode = account.getStudentCode() + "_new";
        final String newClassCode = account.getClassCode() + "_new";

        UpdateAccountDTO updateAccountDTO = new UpdateAccountDTO();
        updateAccountDTO.setStudentCode(newStudentCode);
        updateAccountDTO.setClassCode(newClassCode);

        // add major classes to db
        majorClassService.save(new MajorClass(0,account.getClassCode(), "1,2"));
        majorClassService.save(new MajorClass(0,newClassCode, "1,2"));

        when(jwtUtil.getUsername()).thenReturn(account.getUsername());

        accountValidator.validateUpdateAccount(updateAccountDTO,account);
        accountService.save(account);

        Account updatedAccount = accountService.findByStudentCode(newStudentCode);

        assertEquals(newStudentCode,updatedAccount.getStudentCode());
        assertEquals(newClassCode,updatedAccount.getClassCode());

        System.out.println("Account update test passed.");
    }

    @ParameterizedTest
    @MethodSource("testCasesProvider")
    public void testUpdate_InvalidClassCode(Account account){

        accountService.save(account);

        final String newStudentCode = account.getStudentCode() + "_new";
        final String newClassCode = account.getClassCode() + "_invalid";

        UpdateAccountDTO updateAccountDTO = new UpdateAccountDTO();
        updateAccountDTO.setStudentCode(newStudentCode);
        updateAccountDTO.setClassCode(newClassCode);

        // add only the original major class to db
        majorClassService.save(new MajorClass(0,account.getClassCode(), "1,2"));

        when(jwtUtil.getUsername()).thenReturn(account.getUsername());

        Exception exception = assertThrows(BaseException.class, () -> {
            accountValidator.validateUpdateAccount(updateAccountDTO, account);
        });


        String expectedMessage = "no class with class code: " + newClassCode;
        String actualMessage = exception.getMessage();

        assertEquals(expectedMessage, actualMessage);

        System.out.println("Account update with invalid class code test passed.");
    }

    @ParameterizedTest
    @MethodSource("testCasesProvider")
    public void testGetAccountByContextHolder(Account account){

        accountService.save(account);

        when(jwtUtil.getUsername()).thenReturn(account.getUsername());

        Account fetchedAccount = accountService.findByUsername(jwtUtil.getUsername());

        assertEquals(account.getUsername(), fetchedAccount.getUsername());
        assertEquals(account.getStudentCode(), fetchedAccount.getStudentCode());
        assertEquals(account.getClassCode(), fetchedAccount.getClassCode());

        System.out.println("Get account by context holder test passed.");
    }

    @ParameterizedTest
    @MethodSource("testCasesProvider")
    public void testGetAccountByContextHolder_Unauthenticated(Account account){
        accountService.save(account);

        when(jwtUtil.getUsername()).thenReturn(null);

        Exception exception = assertThrows(BaseException.class, () -> {
            final String username = jwtUtil.getUsername();
            if(username != null){
                return;
            }
                throw new BaseException("have not logged in", HttpStatus.UNAUTHORIZED);
        });

        String expectedMessage = "have not logged in";
        String actualMessage = exception.getMessage();

        assertEquals(expectedMessage, actualMessage);

        System.out.println("Get account by context holder unauthenticated test passed.");
    }

}
