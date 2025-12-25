package test.MajorClass;

import test.IntegrationTest;
import main.entity.MajorClass;
import main.exception.BaseException;
import main.service.MajorClassService;
import main.validator.MajorClassValidator;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class MajorClassIT extends IntegrationTest {

    @Autowired
    MajorClassService majorClassService;

    @Autowired
    MajorClassValidator majorClassValidator;

    MajorClassServiceTestUtil majorClassServiceTestUtil = new MajorClassServiceTestUtil();

    @Test
    public void testAdd_Success() {
        // Setup
        MajorClass majorClass = majorClassServiceTestUtil.getTestCase().getFirst();

        majorClassValidator.validateAddRequest(majorClass);
        MajorClass saved = majorClassService.save(majorClass);

        assertNotNull(saved);
        assertTrue(saved.getId() > 0, "ID must be greater than 0");
        assertEquals("SE1801", saved.getClassCode(), "Class codes mismatch");
        assertEquals("1,2", saved.getSlot(), "Slots mismatch");
    }

    @Test
    public void testAdd_DuplicateClassCode() {
        // Setup: add existing class
        MajorClass existingClass = majorClassServiceTestUtil.getTestCase().getFirst();
        majorClassValidator.validateAddRequest(existingClass);
        majorClassService.save(existingClass);

        // Try to add duplicate
        MajorClass duplicateClass = new MajorClass(0, "SE1801", "3,4");

        BaseException exception = assertThrows(BaseException.class, () -> {
            majorClassValidator.validateAddRequest(duplicateClass);
        });

        assertEquals(HttpStatus.CONFLICT, exception.getHttpStatus(), "HTTP status should be BAD_REQUEST");
        assertTrue(exception.getMessage().contains("existed class with class code"), "Error message mismatch");
    }

    @Test
    public void testUpdate_Success() {
        // Setup: add initial class
        MajorClass majorClass = majorClassServiceTestUtil.getTestCase().getFirst();
        majorClassValidator.validateAddRequest(majorClass);
        MajorClass saved = majorClassService.save(majorClass);

        // Update slot
        saved.setSlot("3,4");
        majorClassValidator.validateUpdateRequest(saved);
        MajorClass updated = majorClassService.save(saved);

        assertEquals(majorClass.getSlot(), updated.getSlot(), "Slots mismatch");
        assertEquals(majorClass.getClassCode(), updated.getClassCode(), "Class codes should remain same");
    }

    @Test
    public void testUpdate_NonExistentClassCode() {
        // Try to update non-existent class
        MajorClass nonExistentClass = majorClassServiceTestUtil.getTestCase().getFirst();

        BaseException exception = assertThrows(BaseException.class, () -> {
            majorClassValidator.validateUpdateRequest(nonExistentClass);
        });

        assertEquals(HttpStatus.NOT_FOUND, exception.getHttpStatus(), "HTTP status should be NOT_FOUND");
        assertTrue(exception.getMessage().contains("not found class with class code"), "Error message mismatch");
    }

    @Test
    public void testFindAll_Success() {
        // Setup: add multiple classes
        MajorClass class1 = majorClassServiceTestUtil.getTestCase().getFirst();
        MajorClass class2 = majorClassServiceTestUtil.getTestCase().get(1);
        majorClassValidator.validateAddRequest(class1);
        majorClassValidator.validateAddRequest(class2);
        majorClassService.save(class1);
        majorClassService.save(class2);

        List<MajorClass> results = majorClassService.findAll(0);

        assertNotNull(results);
        assertFalse(results.isEmpty(), "Results should not be empty");
        assertTrue(results.size() >= 2, "Should have at least 2 classes");
    }

    @Test
    public void testFindAll_InvalidPage() {
        // Setup: add a class first
        MajorClass majorClass = majorClassServiceTestUtil.getTestCase().getFirst();
        majorClassValidator.validateAddRequest(majorClass);
        majorClassService.save(majorClass);

        // Try to access invalid page (negative page handled in controller, but test for empty page)
        BaseException exception = assertThrows(BaseException.class, () -> {
            majorClassService.findAll(999); // Page that doesn't exist
        });

        assertEquals(HttpStatus.NOT_FOUND, exception.getHttpStatus(), "HTTP status should be NOT_FOUND");
        assertTrue(exception.getMessage().contains("no major class found"), "Error message mismatch");
    }
}

