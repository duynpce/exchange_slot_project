package ExchangeSlotRequest;

import Main.DTO.ExchangeSlotRequest.CreateExchangeSlotRequestDTO;
import Main.DTO.ExchangeSlotRequest.ExchangeSlotRequestResponseDTO;
import Main.Entity.ExchangeSlotRequest;
import Main.Mapper.ExchangeSlotRequestMapper;
import Main.Mapper.ExchangeSlotRequestMapperImpl;
import Main.Repository.ExchangeSlotRequestRepository;
import Main.Service.ExchangeSlotRequestService;
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
public class ExchangeSlotRequestServiceTest {

    @Mock
    ExchangeSlotRequestRepository repository;

    @Mock
    ExchangeSlotRequestMapper mapper = new ExchangeSlotRequestMapper() {
        @Override
        public ExchangeSlotRequest toEntity(CreateExchangeSlotRequestDTO dto) {
            return null;
        }


        @Override
        public ExchangeSlotRequestResponseDTO toDto(ExchangeSlotRequest request) {
            ExchangeSlotRequestResponseDTO dto = new ExchangeSlotRequestResponseDTO();
            dto.setId(request.getId());
            dto.setStudentCode(request.getStudentCode());
            dto.setDesiredSlot(request.getDesiredSlot());
            dto.setCurrentSlot(request.getCurrentSlot());
            return dto;
        }

        @Override
        public List<ExchangeSlotRequestResponseDTO> toDtoList(List<ExchangeSlotRequest> requests) {
            if (requests == null) {
                return new ArrayList<>();
            }

            List<ExchangeSlotRequestResponseDTO> dtoList = new ArrayList<>();
            for (ExchangeSlotRequest request : requests) {
                dtoList.add(toDto(request));
            }
            return dtoList;
        }
    };

    @InjectMocks
    ExchangeSlotRequestService service;

    int pageNumber = 0;
    int pageSize = 20;
    Pageable page = PageRequest.of(pageNumber, pageSize);

    ExchangeSlotRequestServiceTestUtil serviceTestUtil = new ExchangeSlotRequestServiceTestUtil();


    @Test
    public void testAdd() {
        System.out.println("Running testAdd...");
        List<ExchangeSlotRequest> testCases = serviceTestUtil.getTestCase();

        for (int i = 0; i < testCases.size(); i++) {
            ExchangeSlotRequest expected = testCases.get(i);

            // Mock repository save
            when(repository.save(expected)).thenReturn(expected);

            ExchangeSlotRequest result = service.add(expected);

            // Kiểm tra xem ID của kết quả có khác ID mặc định là 0 không (do auto-generate ID)
            assertNotNull(result, "#testCase " + (i + 1) + " failed: result is null");
            assertEquals( "#testCase " + (i + 1) + " failed: ID mismatch after add"
                    ,expected.getId(), result.getId());
            System.out.println("#testCase " + (i + 1) + " passed: ExchangeSlotRequest ID " + result.getId());

            verify(repository, times(1)).save(expected);
        }
        System.out.println("add ExchangeSlotRequest passed \n");
    }

    @Test
    public void testDeleteById() {
        System.out.println("Running testDeleteById...");
        List<ExchangeSlotRequest> testCases = serviceTestUtil.getTestCase();

        for (int i = 0; i < testCases.size(); i++) {
            int idToDelete = i + 1; // ID cần xóa

            // Mock repository delete
            doNothing().when(repository).deleteById(idToDelete);

            service.deleteById(testCases.get(i));

            // check call deleteById once
            verify(repository, times(1)).deleteById(idToDelete);
            System.out.println("#testCase " + (i + 1) + " passed: Deleted ExchangeSlotRequest ID " + idToDelete);
        }
        System.out.println("deleteById ExchangeSlotRequest passed \n");
    }

    @Test
    public void testFindById() {
        System.out.println("Running testFindById...");
        List<ExchangeSlotRequest> testCases = serviceTestUtil.getTestCase();

        for (int i = 0; i < testCases.size(); i++) {
            ExchangeSlotRequest expected = testCases.get(i);
            expected.setId(i + 1);

            when(repository.findById(expected.getId())).thenReturn(Optional.of(expected));

            ExchangeSlotRequest result = service.findById(expected.getId());

            assertNotNull(result, "#testCase " + (i + 1) + " failed: result is null");
            assertEquals("#testCase " + (i + 1) + " failed: studentCode mismatch"
                    ,expected.getStudentCode(), result.getStudentCode());
            assertEquals( "#testCase " + (i + 1) + " failed: ID mismatch",expected.getId(), result.getId());

            System.out.println("#testCase " + (i + 1) + " passed: found ExchangeSlotRequest ID " + result.getId());
            verify(repository, times(1)).findById(expected.getId());
        }
        System.out.println("find by id passed \n");
    }

    @Test
    public void testFindBySlot() {
        System.out.println("Running testFindBySlot...");
        List<ExchangeSlotRequest> testCases = serviceTestUtil.getTestCase();

        for (int i = 0; i < testCases.size(); i++) {
            ExchangeSlotRequest input = testCases.get(i);

            when(repository.findByCurrentSlot(input.getCurrentSlot(), page)).thenReturn(testCases);

            List<ExchangeSlotRequest> result = service.findBySlot(input.getCurrentSlot(), pageNumber);

            assertNotNull(result, "#testCase " + (i + 1) + " failed: result is null");
            assertFalse(result.isEmpty(), "#testCase " + (i + 1) + " failed: empty result list");
            assertEquals("#testCase " + (i + 1) + " failed: slot mismatch"
                    ,input.getCurrentSlot(), result.get(i).getCurrentSlot());

            System.out.println("#testCase " + (i + 1) + " passed: found ExchangeSlotRequest with slot " + input.getCurrentSlot());
            verify(repository, times(1)).findByCurrentSlot(input.getCurrentSlot(), page);
        }
        System.out.println("find by slot passed \n");
    }
}
