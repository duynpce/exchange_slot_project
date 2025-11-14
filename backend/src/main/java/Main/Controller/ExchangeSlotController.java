package Main.Controller;

import Main.DTO.ExchangeSlotRequest.CreateExchangeSlotRequestDTO;
import Main.DTO.ExchangeSlotRequest.ExchangeSlotRequestResponseDTO;
import Main.DTO.Common.ResponseDTO;
import Main.Exception.BaseException;
import Main.Entity.ExchangeSlotRequest;
import Main.Mapper.ExchangeSlotRequestMapper;
import Main.Service.ExchangeSlotRequestService;
import Main.Validator.ExchangeSlotRequestValidator;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/exchange_slot")
@RequiredArgsConstructor
public class ExchangeSlotController {

    private final ExchangeSlotRequestService exchangeSlotRequestService;

    private final ExchangeSlotRequestValidator slotRequestValidator;

    private final ExchangeSlotRequestMapper exchangeSlotRequestMapper;

    @PostMapping
    public ResponseEntity<ResponseDTO<String>> add(@RequestBody CreateExchangeSlotRequestDTO request) {

        ExchangeSlotRequest exchangeSlotRequest = exchangeSlotRequestMapper.toEntity(request);
        slotRequestValidator.validateAddRequest(exchangeSlotRequest);
        exchangeSlotRequestService.add(exchangeSlotRequest);

        ResponseDTO<String> response = new ResponseDTO<>(
                true,
                "slot request added successfully",
                "no error",
                "no data"
        );
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @DeleteMapping("/id/{id}")
    public ResponseEntity<ResponseDTO<String>> delete(@PathVariable int id) {
        if(id < 0){
            throw new BaseException("id must be >= 0", HttpStatus.BAD_REQUEST);
        }

        ExchangeSlotRequest request = exchangeSlotRequestService.findById(id);
        exchangeSlotRequestService.deleteById(request);

        ResponseDTO<String> response = new ResponseDTO<>(
                true,
                "slot request deleted successfully",
                "no error",
                "no data"
        );
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @GetMapping("/class/{classCode}/page/{page}")/// add pagination to it please pageable page
    public ResponseEntity<ResponseDTO<List<ExchangeSlotRequestResponseDTO>>> findByClassCode
            (@PathVariable String classCode,
            @PathVariable int page) {
        if(page < 0){
            throw new BaseException("page must be >= 0", HttpStatus.BAD_REQUEST);
        }

        List<ExchangeSlotRequestResponseDTO> result = exchangeSlotRequestMapper.
                toDtoList(exchangeSlotRequestService.findByClassCode(classCode, page));


        ResponseDTO<List<ExchangeSlotRequestResponseDTO>> response = new ResponseDTO<>(
                true,
                "slot request(s) found successfully",
                "no error",
                result
        );

        return ResponseEntity.status(HttpStatus.OK).body(response);
    }
    /// for exchange subject request
//    @GetMapping("/subject/{subjectCode}/page/{page}")/// add pagination to it please pageable page
//    public ResponseEntity<ResponseDTO<List<ExchangeSlotRequest>>> findBySubjectCode
//            (@PathVariable String subjectCode,
//             @PathVariable int page) {
//        if(page < 0){
//            throw new BaseException("page must be >= 0", HttpStatus.BAD_REQUEST);
//        }
//
//        List<ExchangeSlotRequest> result = exchangeSlotRequestService.findBySubjectCode(subjectCode,page);
//
//        ResponseDTO<List<ExchangeSlotRequest>> response = new ResponseDTO<>(
//                true,
//                "slot request(s) found successfully",
//                "no error",
//                result
//        );
//        return ResponseEntity.status(HttpStatus.OK).body(response);
//    }

//    @GetMapping("/subject_code&class_code/")/// add pagination to it please pageable page
//    public ResponseEntity<ResponseDTO<List<ExchangeSlotRequest>>> findBySubjectAndClassCode(
//            @RequestParam("subjectCode") String subjectCode,
//            @RequestParam("classCode") String classCode,
//            @RequestParam("page") int page
//    ) {
//        if(page < 0){
//            throw new BaseException("page must be >= 0", HttpStatus.BAD_REQUEST);
//        }
//
//        List<ExchangeSlotRequest> result =
//                exchangeSlotRequestService.findByClassCodeAndSubjectCode(classCode, subjectCode, page);
//
//        ResponseDTO<List<ExchangeSlotRequest>> response = new ResponseDTO<>(
//                true,
//                "slot request(s) found successfully",
//                "no error",
//                result
//        );
//        return ResponseEntity.status(HttpStatus.OK).body(response);
//    }

    @GetMapping("/slot/{slot}/page/{page}") /// add pagination to it please pageable page
    public ResponseEntity<ResponseDTO<List<ExchangeSlotRequestResponseDTO>>> findBySlot
            (@PathVariable String slot, @PathVariable int page) {

        if(page < 0){
            throw new BaseException("page must be >= 0", HttpStatus.BAD_REQUEST);
        }

        List<ExchangeSlotRequestResponseDTO> data = exchangeSlotRequestMapper
                .toDtoList(exchangeSlotRequestService.findBySlot(slot, page));

        ResponseDTO<List<ExchangeSlotRequestResponseDTO>> response = new ResponseDTO<>(
                true,
                "slot request(s) found successfully",
                "no error",
                data
        );
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @GetMapping("/student_code/{studentCode}")
    public ResponseEntity<ResponseDTO<ExchangeSlotRequestResponseDTO>> findByStudentCode(
            @PathVariable String studentCode
    ) {
        ExchangeSlotRequestResponseDTO data = exchangeSlotRequestMapper
                .toDto(exchangeSlotRequestService.findByStudentCode(studentCode));

        ResponseDTO<ExchangeSlotRequestResponseDTO> response =
                new ResponseDTO<>(true, "request found successfully", "no error", data);

        return ResponseEntity.status(HttpStatus.OK).body(response);
    }
}
