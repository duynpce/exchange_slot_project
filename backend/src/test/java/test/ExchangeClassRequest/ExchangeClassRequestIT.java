package test.ExchangeClassRequest;

import test.Account.AccountServiceTestUtil;
import test.IntegrationTest;
import main.entity.Account;
import main.entity.ExchangeClassRequest;
import main.entity.MajorClass;
import main.exception.BaseException;
import main.service.AccountService;
import main.service.ExchangeClassRequestService;
import main.service.MajorClassService;
import main.utility.JwtUtil;
import main.validator.ExchangeClassRequestValidator;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.bean.override.mockito.MockitoBean;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class ExchangeClassRequestIT extends IntegrationTest {

    @Autowired
    ExchangeClassRequestService exchangeClassRequestService;

    @Autowired
    ExchangeClassRequestValidator exchangeClassRequestValidator;

    @Autowired
    MajorClassService majorClassService;

    @Autowired
    AccountService accountService;

    @MockitoBean
    JwtUtil jwtUtil;

    AccountServiceTestUtil accountServiceTestUtil = new AccountServiceTestUtil();

    @Test
    public void testAdd_Success() {
        // Setup: add major classes and account
        Account account = accountServiceTestUtil.getTestCase().getFirst();
        majorClassService.save(new MajorClass(0, account.getClassCode(), "1,2"));
        majorClassService.save(new MajorClass(0, "SE1802", "3,4"));
        accountService.save(account);

        ExchangeClassRequest request = new ExchangeClassRequest();
        request.setStudentCode(account.getStudentCode());
        request.setDesiredClassCode("SE1802");

        exchangeClassRequestValidator.validateAddRequest(request);
        ExchangeClassRequest saved = exchangeClassRequestService.add(request);

        assertNotNull(saved);
        assertTrue(saved.getId() > 0, "ID must be greater than 0");
        assertEquals(account.getStudentCode(), saved.getStudentCode(), "Student codes mismatch");
        assertEquals("SE1802", saved.getDesiredClassCode(), "Desired class codes mismatch");


    }

    @Test
    public void testAdd_SameSlot() {
        // Setup: add major class with same slot
        Account account = accountServiceTestUtil.getTestCase().getFirst();
        majorClassService.save(new MajorClass(0, account.getClassCode(), "1,2"));
        majorClassService.save(new MajorClass(0, "SE1802", "1,2")); // Same slot
        accountService.save(account);

        ExchangeClassRequest request = new ExchangeClassRequest();
        request.setStudentCode(account.getStudentCode());
        request.setDesiredClassCode("SE1802");

        BaseException exception = assertThrows(BaseException.class, () -> {
            exchangeClassRequestValidator.validateAddRequest(request);
        });

        assertEquals(HttpStatus.BAD_REQUEST, exception.getHttpStatus(), "HTTP status should be BAD_REQUEST");
    }

   @Test
    public void testUpdate_Success() {

        // Setup
        Account account = accountServiceTestUtil.getTestCase().getFirst();
        majorClassService.save(new MajorClass(0, account.getClassCode(), "1"));
        majorClassService.save(new MajorClass(0, "SE1802", "2"));
        majorClassService.save(new MajorClass(0, "SE1803", "3"));
        accountService.save(account);

        ExchangeClassRequest request = new ExchangeClassRequest();
        request.setStudentCode(account.getStudentCode());
        request.setDesiredClassCode("SE1802");
        exchangeClassRequestValidator.validateAddRequest(request);
        ExchangeClassRequest saved = exchangeClassRequestService.add(request);

        // Update to different class
        saved.setDesiredClassCode("SE1803");
        exchangeClassRequestValidator.validateUpdateRequest(saved);
        ExchangeClassRequest updated = exchangeClassRequestService.update(saved);

        assertEquals("SE1803", updated.getDesiredClassCode(),"class code mismatch");
    }


    @Test
    public void testUpdate_SameSlot() {

        // Setup
        Account account = accountServiceTestUtil.getTestCase().getFirst();
        majorClassService.save(new MajorClass(0, account.getClassCode(), "1,2"));
        majorClassService.save(new MajorClass(0, "SE1802", "3,4"));
        majorClassService.save(new MajorClass(0, "SE1803", "1,2")); // Same slot as current
        accountService.save(account);

        ExchangeClassRequest request = new ExchangeClassRequest();
        request.setStudentCode(account.getStudentCode());
        request.setDesiredClassCode("SE1802");
        exchangeClassRequestValidator.validateAddRequest(request);
        ExchangeClassRequest saved = exchangeClassRequestService.add(request);

        // Try to update to class with same slot
        saved.setDesiredClassCode("SE1803");

        BaseException exception = assertThrows(BaseException.class, () -> {
            exchangeClassRequestValidator.validateUpdateRequest(saved);
        });

        assertEquals(HttpStatus.BAD_REQUEST, exception.getHttpStatus(), "HTTP status should be BAD_REQUEST");
    }

    @Test
    public void testDelete_Success() {
        // Setup
        Account account = accountServiceTestUtil.getTestCase().getFirst();
        majorClassService.save(new MajorClass(0, account.getClassCode(), "1"));
        majorClassService.save(new MajorClass(0, "SE1802", "2"));
        accountService.save(account);

        ExchangeClassRequest request = new ExchangeClassRequest();
        request.setStudentCode(account.getStudentCode());
        request.setDesiredClassCode("SE1802");
        exchangeClassRequestValidator.validateAddRequest(request);
        ExchangeClassRequest saved = exchangeClassRequestService.add(request);

        int id = saved.getId();
        exchangeClassRequestService.deleteById(saved);

        BaseException exception = assertThrows(BaseException.class, () -> {
            exchangeClassRequestService.findById(id);
        });

        // deletion successful if not found
        assertEquals(HttpStatus.NOT_FOUND, exception.getHttpStatus(), "HTTP status should be NOT_FOUND");
    }

    @Test
    public void testDelete_InvalidId() {

        Account account = accountServiceTestUtil.getTestCase().getFirst();
        BaseException exception = assertThrows(BaseException.class, () -> {
            exchangeClassRequestService.findById(99999);
        });

        assertEquals(HttpStatus.NOT_FOUND, exception.getHttpStatus(), "HTTP status should be NOT_FOUND");
    }

    @Test
    public void testFindByClassCode_Success() {
        // Setup
        Account account = accountServiceTestUtil.getTestCase().getFirst();
        majorClassService.save(new MajorClass(0, account.getClassCode(), "1"));
        majorClassService.save(new MajorClass(0, "SE1802", "2"));
        accountService.save(account);

        ExchangeClassRequest request = new ExchangeClassRequest();
        request.setStudentCode(account.getStudentCode());
        request.setDesiredClassCode("SE1802");
        exchangeClassRequestValidator.validateAddRequest(request);
        exchangeClassRequestService.add(request);

        List<ExchangeClassRequest> results = exchangeClassRequestService.findByClassCode(account.getClassCode(), 0);

        assertEquals(account.getClassCode(), results.getFirst().getCurrentClassCode(), "Class codes mismatch");
    }

    @Test
    public void testFindByClassCode_NoResults() {

        BaseException exception = assertThrows(BaseException.class, () -> {
            exchangeClassRequestService.findByClassCode("NONEXISTENT", 0);
        });

        assertEquals(HttpStatus.NOT_FOUND, exception.getHttpStatus(), "HTTP status should be NOT_FOUND");
    }

    @Test
    public void testFindBySlot_Success() {
        // Setup
        Account account = accountServiceTestUtil.getTestCase().getFirst();
        majorClassService.save(new MajorClass(0, account.getClassCode(), "1,2"));
        majorClassService.save(new MajorClass(0, "SE1802", "3,4"));
        accountService.save(account);

        ExchangeClassRequest request = new ExchangeClassRequest();
        request.setStudentCode(account.getStudentCode());
        request.setDesiredClassCode("SE1802");
        exchangeClassRequestValidator.validateAddRequest(request);
        exchangeClassRequestService.add(request);

        List<ExchangeClassRequest> results = exchangeClassRequestService.findBySlot("1,2", 0);
        ExchangeClassRequest foundRequest = results.getFirst();

        assertEquals("1,2", foundRequest.getCurrentSlot(), "Slots mismatch");
    }

    @Test
    public void testFindBySlot_NoResults() {

        BaseException exception = assertThrows(BaseException.class, () -> {
            exchangeClassRequestService.findBySlot("NONEXISTENT", 0);
        });

        assertEquals(HttpStatus.NOT_FOUND, exception.getHttpStatus(), "HTTP status should be NOT_FOUND");
    }

    @Test
    public void testFindByStudentCode_Success() {
        // Setup
        Account account = accountServiceTestUtil.getTestCase().getFirst();
        majorClassService.save(new MajorClass(0, account.getClassCode(), "1,2"));
        majorClassService.save(new MajorClass(0, "SE1802", "3,4"));
        accountService.save(account);

        ExchangeClassRequest request = new ExchangeClassRequest();
        request.setStudentCode(account.getStudentCode());
        request.setDesiredClassCode("SE1802");
        exchangeClassRequestValidator.validateAddRequest(request);
        exchangeClassRequestService.add(request);

        ExchangeClassRequest result = exchangeClassRequestService.findByStudentCode(account.getStudentCode());

        assertEquals(account.getStudentCode(), result.getStudentCode(), "Student mismatch");
    }

    @Test
    public void testFindByStudentCode_NotFound() {

        BaseException exception = assertThrows(BaseException.class, () -> {
            exchangeClassRequestService.findByStudentCode("NONEXISTENT");
        });

        assertEquals(HttpStatus.NOT_FOUND, exception.getHttpStatus(), "HTTP status should be NOT_FOUND");
    }
}
