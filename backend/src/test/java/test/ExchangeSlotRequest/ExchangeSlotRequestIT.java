package test.ExchangeSlotRequest;

import test.Account.AccountServiceTestUtil;
import test.IntegrationTest;
import main.entity.Account;
import main.entity.ExchangeSlotRequest;
import main.entity.MajorClass;
import main.exception.BaseException;
import main.service.AccountService;
import main.service.ExchangeSlotRequestService;
import main.service.MajorClassService;
import main.utility.JwtUtil;
import main.validator.ExchangeSlotRequestValidator;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.bean.override.mockito.MockitoBean;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class ExchangeSlotRequestIT extends IntegrationTest {

    @Autowired
    ExchangeSlotRequestService exchangeSlotRequestService;

    @Autowired
    ExchangeSlotRequestValidator exchangeSlotRequestValidator;

    @Autowired
    AccountService accountService;

    @Autowired
    MajorClassService majorClassService;

    @MockitoBean
    JwtUtil jwtUtil;

    AccountServiceTestUtil accountServiceTestUtil = new AccountServiceTestUtil();

    @Test
    public void testAdd_Success() {
        // Setup: add major class and account
        Account account = accountServiceTestUtil.getTestCase().getFirst();
        majorClassService.save(new MajorClass(0, account.getClassCode(), "1,2"));
        accountService.save(account);

        ExchangeSlotRequest request = new ExchangeSlotRequest();
        request.setStudentCode(account.getStudentCode());
        request.setDesiredSlot("3,4");

        exchangeSlotRequestValidator.validateAddRequest(request);
        ExchangeSlotRequest saved = exchangeSlotRequestService.add(request);

        assertNotNull(saved);
        assertTrue(saved.getId() > 0, "ID must be greater than 0");
        assertEquals(account.getStudentCode(), saved.getStudentCode(), "Student codes mismatch");
        assertEquals("3,4", saved.getDesiredSlot(), "Desired slots mismatch");
        assertEquals("1,2", saved.getCurrentSlot(), "Current slots mismatch");
    }

    @Test
    public void testAdd_SameSlot() {
        // Setup: add major class and account
        Account account = accountServiceTestUtil.getTestCase().getFirst();
        majorClassService.save(new MajorClass(0, account.getClassCode(), "1,2"));
        accountService.save(account);

        ExchangeSlotRequest request = new ExchangeSlotRequest();
        request.setStudentCode(account.getStudentCode());
        request.setDesiredSlot("1,2"); // Same as current slot

        BaseException exception = assertThrows(BaseException.class, () -> {
            exchangeSlotRequestValidator.validateAddRequest(request);
        });

        assertEquals(HttpStatus.BAD_REQUEST, exception.getHttpStatus(), "HTTP status should be BAD_REQUEST");
    }

    @Test
    public void testDelete_Success() {
        // Setup
        Account account = accountServiceTestUtil.getTestCase().getFirst();
        majorClassService.save(new MajorClass(0, account.getClassCode(), "1,2"));
        accountService.save(account);

        ExchangeSlotRequest request = new ExchangeSlotRequest();
        request.setStudentCode(account.getStudentCode());
        request.setDesiredSlot("3,4");

        exchangeSlotRequestValidator.validateAddRequest(request);
        ExchangeSlotRequest saved = exchangeSlotRequestService.add(request);

        int id = saved.getId();
        exchangeSlotRequestService.deleteById(saved);

        BaseException exception = assertThrows(BaseException.class, () -> {
            exchangeSlotRequestService.findById(id);
        });

        // deletion successful if not found
        assertEquals(HttpStatus.NOT_FOUND, exception.getHttpStatus(), "HTTP status should be NOT_FOUND");
    }

    @Test
    public void testDelete_InvalidId() {
        Account account = accountServiceTestUtil.getTestCase().getFirst();

        BaseException exception = assertThrows(BaseException.class, () -> {
            exchangeSlotRequestService.findById(99999);
        });

        assertEquals(HttpStatus.NOT_FOUND, exception.getHttpStatus(), "HTTP status should be NOT_FOUND");
    }

    @Test
    public void testFindByCurrentClassCode_Success() {
        // Setup
        Account account = accountServiceTestUtil.getTestCase().getFirst();
        majorClassService.save(new MajorClass(0, account.getClassCode(), "1,2"));
        accountService.save(account);

        ExchangeSlotRequest request = new ExchangeSlotRequest();
        request.setStudentCode(account.getStudentCode());
        request.setDesiredSlot("3,4");
        exchangeSlotRequestValidator.validateAddRequest(request);
        exchangeSlotRequestService.add(request);

        List<ExchangeSlotRequest> results = exchangeSlotRequestService.findByCurrentClassCode(account.getClassCode(), 0);

        assertEquals(account.getClassCode(), results.get(0).getCurrentClassCode(), "Class codes mismatch");
    }

    @Test
    public void testFindByCurrentClassCode_NoResults() {
        BaseException exception = assertThrows(BaseException.class, () -> {
            exchangeSlotRequestService.findByCurrentClassCode("NONEXISTENT", 0);
        });

        assertEquals(HttpStatus.NOT_FOUND, exception.getHttpStatus(), "HTTP status should be NOT_FOUND");
    }

    @Test
    public void testFindBySlot_Success() {
        // Setup
        Account account = accountServiceTestUtil.getTestCase().getFirst();
        majorClassService.save(new MajorClass(0, account.getClassCode(), "1,2"));
        accountService.save(account);

        ExchangeSlotRequest request = new ExchangeSlotRequest();
        request.setStudentCode(account.getStudentCode());
        request.setDesiredSlot("3,4");
        exchangeSlotRequestValidator.validateAddRequest(request);
        exchangeSlotRequestService.add(request);

        List<ExchangeSlotRequest> results = exchangeSlotRequestService.findBySlot("1,2", 0);
        ExchangeSlotRequest foundRequest = results.get(0);

        assertEquals("1,2", foundRequest.getCurrentSlot(), "Slots mismatch");
    }

    @Test
    public void testFindBySlot_NoResults() {
        BaseException exception = assertThrows(BaseException.class, () -> {
            exchangeSlotRequestService.findBySlot("NONEXISTENT", 0);
        });

        assertEquals(HttpStatus.NOT_FOUND, exception.getHttpStatus(), "HTTP status should be NOT_FOUND");
    }

    @Test
    public void testFindByStudentCode_Success() {
        // Setup
        Account account = accountServiceTestUtil.getTestCase().getFirst();
        majorClassService.save(new MajorClass(0, account.getClassCode(), "1,2"));
        accountService.save(account);

        ExchangeSlotRequest request = new ExchangeSlotRequest();
        request.setStudentCode(account.getStudentCode());
        request.setDesiredSlot("3,4");
        exchangeSlotRequestValidator.validateAddRequest(request);
        exchangeSlotRequestService.add(request);

        ExchangeSlotRequest result = exchangeSlotRequestService.findByStudentCode(account.getStudentCode());

        assertEquals(account.getStudentCode(), result.getStudentCode(), "Student mismatch");
    }

    @Test
    public void testFindByStudentCode_NotFound() {
        BaseException exception = assertThrows(BaseException.class, () -> {
            exchangeSlotRequestService.findByStudentCode("NONEXISTENT");
        });

        assertEquals(HttpStatus.NOT_FOUND, exception.getHttpStatus(), "HTTP status should be NOT_FOUND");
    }
}
