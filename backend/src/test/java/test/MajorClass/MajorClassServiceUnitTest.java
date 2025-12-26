package test.MajorClass;

import main.entity.MajorClass;
import main.repository.MajorClassRepository;
import main.service.MajorClassService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.util.AssertionErrors.assertEquals;

@ExtendWith(MockitoExtension.class)
public class MajorClassServiceUnitTest {

    @Mock
    MajorClassRepository repository;

    @InjectMocks
    MajorClassService service;

    int pageNumber = 0;
    int pageSize = 15;
    Pageable page = PageRequest.of(pageNumber, pageSize);

    MajorClassServiceTestUtil serviceTestUtil = new MajorClassServiceTestUtil();

    @Test
    public void testSave() {
        System.out.println("Running testSave...");
        List<MajorClass> testCases = serviceTestUtil.getTestCase();

        for (int i = 0; i < testCases.size(); i++) {
            MajorClass expected = testCases.get(i);

            when(repository.save(expected)).thenReturn(expected);

            MajorClass result = service.save(expected);

            assertNotNull(result, "#testCase " + (i + 1) + " failed: result is null");
            assertEquals("#testCase " + (i + 1) + " failed: ID mismatch after save",
                    expected.getId(), result.getId());
            assertEquals("#testCase " + (i + 1) + " failed: classCode mismatch",
                    expected.getClassCode(), result.getClassCode());
            System.out.println("#testCase " + (i + 1) + " passed: MajorClass ID " + result.getId());

            verify(repository, times(1)).save(expected);
        }
        System.out.println("save MajorClass passed \n");
    }

    @Test
    public void testFindByClassCode() {
        System.out.println("Running testFindByClassCode...");
        List<MajorClass> testCases = serviceTestUtil.getTestCase();

        for (int i = 0; i < testCases.size(); i++) {
            MajorClass expected = testCases.get(i);
            expected.setId(i + 1);

            when(repository.findByClassCode(expected.getClassCode())).thenReturn(Optional.of(expected));

            MajorClass result = service.findByClassCode(expected.getClassCode());

            assertNotNull(result, "#testCase " + (i + 1) + " failed: result is null");
            assertEquals("#testCase " + (i + 1) + " failed: classCode mismatch",
                    expected.getClassCode(), result.getClassCode());
            assertEquals("#testCase " + (i + 1) + " failed: ID mismatch",
                    expected.getId(), result.getId());

            System.out.println("#testCase " + (i + 1) + " passed: found MajorClass with classCode " + result.getClassCode());
            verify(repository, times(1)).findByClassCode(expected.getClassCode());
        }
        System.out.println("find by classCode passed \n");
    }

    @Test
    public void testFindBySlot() {
        System.out.println("Running testFindBySlot...");
        List<MajorClass> testCases = serviceTestUtil.getTestCase();

        for (int i = 0; i < testCases.size(); i++) {
            MajorClass input = testCases.get(i);

            // return input when findBySlot is called
            when(repository.findBySlot(input.getSlot(), page)).thenReturn(List.of(input));

            List<MajorClass> result = service.findBySlot(input.getSlot(), pageNumber);

            assertNotNull(result, "#testCase " + (i + 1) + " failed: result is null");
            assertFalse(result.isEmpty(), "#testCase " + (i + 1) + " failed: empty result list");
            assertEquals("#testCase " + (i + 1) + " failed: slot mismatch",
                    input.getSlot(), result.getFirst().getSlot());

            System.out.println("#testCase " + (i + 1) + " passed: found MajorClass with slot " + input.getSlot());
        }
        System.out.println("find by slot passed \n");
    }

    @Test
    public void testExistsByClassCode() {
        System.out.println("Running testExistsByClassCode...");
        List<MajorClass> testCases = serviceTestUtil.getTestCase();

        for (int i = 0; i < testCases.size(); i++) {
            MajorClass input = testCases.get(i);

            when(repository.existsByClassCode(input.getClassCode())).thenReturn(true);

            boolean result = service.existsByClassCode(input.getClassCode());

            assertTrue(result, "#testCase " + (i + 1) + " failed: should exist");

            System.out.println("#testCase " + (i + 1) + " passed: MajorClass exists with classCode " + input.getClassCode());
            verify(repository, times(1)).existsByClassCode(input.getClassCode());
        }
        System.out.println("existsByClassCode passed \n");
    }

    @Test
    public void testFindAll() {
        System.out.println("Running testFindAll...");
        List<MajorClass> testCases = serviceTestUtil.getTestCase();
        Page<MajorClass> pagedResult = new PageImpl<>(testCases);

        when(repository.findAll(page)).thenReturn(pagedResult);

        List<MajorClass> result = service.findAll(pageNumber);

        assertNotNull(result, "failed: result is null");
        assertFalse(result.isEmpty(), "failed: empty result list");
        assertEquals("failed: size mismatch", testCases.size(), result.size());

        System.out.println("testCase passed: found " + result.size() + " MajorClass records");
        verify(repository, times(1)).findAll(page);

        System.out.println("findAll passed \n");
    }
}
