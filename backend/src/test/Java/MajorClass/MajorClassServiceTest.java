package MajorClass;

import Main.Entity.MajorClass;
import Main.Repository.MajorClassRepository;
import Main.Service.MajorClassService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.util.AssertionErrors.assertEquals;

@ExtendWith(MockitoExtension.class)
public class MajorClassServiceTest {

    @Mock
    MajorClassRepository majorClassRepository;

    @InjectMocks
    MajorClassService majorClassService;

    int pageNumber = 0;
    int pageSize = 20;
    Pageable page = PageRequest.of(pageNumber, pageSize);

    MajorClassServiceTestUtil serviceTestUtil = new MajorClassServiceTestUtil();

    @Test
    public void testAdd() {
        List<MajorClass> testCases = serviceTestUtil.getTestCase();

        for (int i = 0; i < testCases.size(); i++) {
            MajorClass input = testCases.get(i);
            MajorClass expected = new MajorClass();
            expected.setId(i + 1); // Giả sử ID được auto-generate và bắt đầu từ 1

            // Mock repository save
            when(majorClassRepository.save(input)).thenReturn(expected);

            majorClassService.add(input);

            // Kiểm tra việc gọi save trên repository
            verify(majorClassRepository, times(1)).save(input);
            System.out.println("#testCase " + (i + 1) + " passed: MajorClass added with ID " + expected.getId());
        }
        System.out.println("add MajorClass passed \n");
    }

    @Test
    public void testUpdate() {
        List<MajorClass> testCases = serviceTestUtil.getTestCase();

        for (int i = 0; i < testCases.size(); i++) {
            MajorClass input = testCases.get(i);
            input.setClassCode("updatedClassCode" + i);

            MajorClass expected = new MajorClass();
            expected.setId(i + 1);
            expected.setClassCode(input.getClassCode());

            // Mock repository save
            when(majorClassRepository.save(input)).thenReturn(expected);

            majorClassService.update(input);

            // Kiểm tra việc gọi save trên repository
            verify(majorClassRepository, times(1)).save(input);
            System.out.println("#testCase " + (i + 1) + " passed: MajorClass updated");
        }
        System.out.println("update MajorClass passed \n");
    }

    @Test
    public void testFindByClassCode() {
        List<MajorClass> testCases = serviceTestUtil.getTestCase();

        for (int i = 0; i < testCases.size(); i++) {
            MajorClass expected = testCases.get(i);
            expected.setId(i + 1);

            when(majorClassRepository.findByClassCode(expected.getClassCode())).thenReturn(Optional.of(expected));

            MajorClass result = majorClassService.findByClassCode(expected.getClassCode());

            assertNotNull(result, "#testCase " + (i + 1) + " failed: result is null");
            assertEquals("#testCase " + (i + 1) + " failed: classCode mismatch",
                    expected.getClassCode(), result.getClassCode());
            assertEquals( "#testCase " + (i + 1) + " failed: ID mismatch after add",expected.getId(), result.getId());
            System.out.println("#testCase " + (i + 1) + " passed: found MajorClass with classCode " + expected.getClassCode());
            verify(majorClassRepository, times(1)).findByClassCode(expected.getClassCode());
        }
        System.out.println("findByClassCode passed \n");
    }

    @Test
    public void testExistsByClassCode() {
        List<MajorClass> testCases = serviceTestUtil.getTestCase();

        for (int i = 0; i < testCases.size(); i++) {
            String classCode = testCases.get(i).getClassCode();
            boolean expected = i % 2 != 0;  // If i is odd, it exists, if i is even, it doesn't exist

            // Mock repository existsByClassCode
            when(majorClassRepository.existsByClassCode(classCode)).thenReturn(expected);

            boolean result = majorClassService.existsByClassCode(classCode);

            assertEquals("#testCase " + (i + 1) + " failed: exists mismatch", expected, result);
            System.out.println("#testCase " + (i + 1) + " passed: existsByClassCode check for classCode " + classCode);
            verify(majorClassRepository, times(1)).existsByClassCode(classCode);
        }
        System.out.println("existsByClassCode passed \n");
    }
}
