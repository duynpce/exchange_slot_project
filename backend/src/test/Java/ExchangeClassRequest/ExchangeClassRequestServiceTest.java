package ExchangeClassRequest;

import Main.DTO.ExchangeClassRequest.CreateExchangeClassRequestDTO;
import Main.DTO.ExchangeClassRequest.ExchangeClassRequestResponseDTO;
import Main.DTO.ExchangeClassRequest.UpdateExchangeClassRequestDTO;
import Main.Entity.ExchangeClassRequest;
import Main.Mapper.ExchangeClassRequestMapper;
import Main.Mapper.ExchangeClassRequestMapperImpl;
import Main.Repository.ExchangeClassRequestRepository;
import Main.Service.ExchangeClassRequestService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.util.AssertionErrors.assertEquals;

@ExtendWith(MockitoExtension.class)
public class ExchangeClassRequestServiceTest {

    @Mock
    ExchangeClassRequestRepository repository;

    @Mock
    ExchangeClassRequestMapper mapper = new ExchangeClassRequestMapper() {
        @Override
        public ExchangeClassRequest toEntity(CreateExchangeClassRequestDTO dto) {
            return null;
        }

        @Override
        public ExchangeClassRequest toEntity(UpdateExchangeClassRequestDTO dto) {
            return null;
        }

        @Override
        public ExchangeClassRequestResponseDTO toDto(ExchangeClassRequest request) {
            ExchangeClassRequestResponseDTO dto = new ExchangeClassRequestResponseDTO();
            dto.setId(request.getId());
            dto.setStudentCode(request.getStudentCode());
            dto.setDesiredClassCode(request.getDesiredClassCode());
            dto.setCurrentClassCode(request.getCurrentClassCode());
            dto.setDesiredSlot(request.getDesiredSlot());
            dto.setCurrentSlot(request.getCurrentSlot());
            return dto;
        }

        @Override
        public List<ExchangeClassRequestResponseDTO> toDtoList(List<ExchangeClassRequest> requests) {
            if (requests == null) {
                return new ArrayList<>();
            }

            List<ExchangeClassRequestResponseDTO> dtoList = new ArrayList<>();
            for (ExchangeClassRequest request : requests) {
                dtoList.add(toDto(request));
            }
            return dtoList;
        }
    };

    @InjectMocks
    ExchangeClassRequestService service;

    int pageNumber = 0;
    int pageSize = 20;
    Pageable page = PageRequest.of(pageNumber, pageSize);

    ExchangeClassRequestServiceTestUtil serviceTestUtil = new ExchangeClassRequestServiceTestUtil();


//    @BeforeEach
//    public void init() {
//
//    }

    @Test
    public void testAdd() {
        System.out.println("Running testAdd...");
        List<ExchangeClassRequest> testCases = serviceTestUtil.getTestCase();

        for (int i = 0; i < testCases.size(); i++) {
            ExchangeClassRequest expected = testCases.get(i);

            // Mock repository và encoder
            when(repository.save(expected)).thenReturn(expected);

            ExchangeClassRequest result = service.add(expected);

            // Kiểm tra xem ID của kết quả có khác ID mặc định là 0 không (do auto-generate ID)
            assertNotNull(result, "#testCase " + (i + 1) + " failed: result is null");
            assertEquals( "#testCase " + (i + 1) + " failed: ID mismatch after add" ,expected.getId(), result.getId());
            System.out.println("#testCase " + (i + 1) + " passed: ExchangeClassRequest ID " + result.getId());

            verify(repository, times(1)).save(expected);
        }
        System.out.println("add ExchangeClassRequest passed \n");
    }

    @Test
    public void testUpdate() {
        System.out.println("Running testUpdate...");
        List<ExchangeClassRequest> testCases = serviceTestUtil.getTestCase();

        for (int i = 0; i < testCases.size(); i++) {
            ExchangeClassRequest input = testCases.get(i);
            input.setDesiredClassCode("updatedClassCode" + i);

            ExchangeClassRequest expected = new ExchangeClassRequest();
            expected.setId(i + 1);
            expected.setDesiredClassCode(input.getDesiredClassCode());

            // Mock repository update
            when(repository.save(input)).thenReturn(expected);

            ExchangeClassRequest result = service.update(input);

            assertNotNull(result, "#testCase " + (i + 1) + " failed: result is null");
            assertEquals("#testCase " + (i + 1) + " failed: desiredClassCode mismatch after update"
                    ,expected.getDesiredClassCode(), result.getDesiredClassCode());
            System.out.println("#testCase " + (i + 1) + " passed: ExchangeClassRequest updated");

            verify(repository, times(1)).save(input);
        }
        System.out.println("update ExchangeClassRequest passed \n");
    }

    @Test
    public void testDeleteById() {
        System.out.println("Running testDeleteById...");
        List<ExchangeClassRequest> testCases = serviceTestUtil.getTestCase();

        for (int i = 0; i < testCases.size(); i++) {
            int idToDelete = i + 1; // ID cần xóa

            // Mock repository delete
            doNothing().when(repository).deleteById(idToDelete);

            service.deleteById(testCases.get(i));

            // check call deleteById once
            verify(repository, times(1)).deleteById(idToDelete);
            System.out.println("#testCase " + (i + 1) + " passed: Deleted ExchangeClassRequest ID " + idToDelete);
        }
        System.out.println("deleteById ExchangeClassRequest passed \n");
    }

    @Test
    public void testFindById() {
        System.out.println("Running testFindById...");
        List<ExchangeClassRequest> testCases = serviceTestUtil.getTestCase();

        for (int i = 0; i < testCases.size(); i++) {
            ExchangeClassRequest expected = testCases.get(i);
            expected.setId(i + 1);

            when(repository.findById(expected.getId())).thenReturn(Optional.of(expected));

            ExchangeClassRequest result = service.findById(expected.getId());

            assertNotNull(result, "#testCase " + (i + 1) + " failed: result is null");
            assertEquals("#testCase " + (i + 1) + " failed: studentCode mismatch"
                    ,expected.getStudentCode(), result.getStudentCode());
            assertEquals( "#testCase " + (i + 1) + " failed: ID mismatch",expected.getId(), result.getId());

            System.out.println("#testCase " + (i + 1) + " passed: found ExchangeClassRequest ID " + result.getId());
            verify(repository, times(1)).findById(expected.getId());
        }
        System.out.println("find by id passed \n");
    }

    @Test
    public void testFindByClassCode() {
        System.out.println("Running testFindByClassCode...");
        List<ExchangeClassRequest> testCases = serviceTestUtil.getTestCase();

        for (int i = 0; i < testCases.size(); i++) {
            ExchangeClassRequest expected = testCases.get(i);
            expected.setId(i + 1);

            when(repository.findByAccount_ClassCode(expected.getCurrentClassCode(),page))
                    .thenReturn(List.of(expected));

            List<ExchangeClassRequest> result = service.findByClassCode(expected.getCurrentClassCode(), pageNumber);

            assertNotNull(result, "#testCase " + (i + 1) + " failed: result is null");
            assertFalse(result.isEmpty(), "#testCase " + (i + 1) + " failed: empty result list");
            assertEquals( "#testCase " + (i + 1) + " failed: classCode mismatch",
                    expected.getCurrentClassCode(), result.getFirst().getCurrentClassCode());

            System.out.println("#testCase " + (i + 1) + " passed: found ExchangeClassRequest with classCode " + expected.getCurrentClassCode());
            verify(repository, times(1)).findByAccount_ClassCode(expected.getCurrentClassCode(), page);
        }
        System.out.println("find by class code passed \n");

    }

    @Test
    public void testFindBySlot() {
        System.out.println("Running testFindBySlot...");
        List<ExchangeClassRequest> testCases = serviceTestUtil.getTestCase();

        for (int i = 0; i < testCases.size(); i++) {
            ExchangeClassRequest input = testCases.get(i);

            when(repository.findByCurrentSlot(input.getCurrentSlot(),page)).thenReturn(testCases);

            List<ExchangeClassRequest> result = service.findBySlot(input.getCurrentSlot(), pageNumber);

            assertNotNull(result, "#testCase " + (i + 1) + " failed: result is null");
            assertFalse(result.isEmpty(), "#testCase " + (i + 1) + " failed: empty result list");
            assertEquals("#testCase " + (i + 1) + " failed: slot mismatch"
                    ,input.getCurrentSlot(), result.get(i).getCurrentSlot());

            System.out.println("#testCase " + (i + 1) + " passed: found ExchangeClassRequest with slot " + input.getCurrentSlot());
            verify(repository, times(1)).findByCurrentSlot(input.getCurrentSlot(),page);
        }
        System.out.println("find by slot passed \n");
    }


}
