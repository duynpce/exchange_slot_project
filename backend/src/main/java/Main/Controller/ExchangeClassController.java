package Main.Controller;


import Main.DTO.ExchangeClassRequest.CreateExchangeClassRequestDTO;
import Main.DTO.ExchangeClassRequest.ExchangeClassRequestResponseDTO;
import Main.DTO.Common.ResponseDTO;
import Main.DTO.ExchangeClassRequest.UpdateExchangeClassRequestDTO;
import Main.Exception.BaseException;
import Main.Entity.ExchangeClassRequest;
import Main.Mapper.ExchangeClassRequestMapper;
import Main.Service.ExchangeClassRequestService;
import Main.Validator.ExchangeClassRequestValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/exchange_class")
@RequiredArgsConstructor
public class ExchangeClassController {

    private final ExchangeClassRequestService exchangeClassRequestService;

    private final ExchangeClassRequestValidator classRequestValidator;

    private final ExchangeClassRequestMapper exchangeClassRequestMapper;

    @PostMapping
    public ResponseEntity<ResponseDTO<String>> add(@RequestBody CreateExchangeClassRequestDTO request) {

        ExchangeClassRequest exchangeClassRequest = exchangeClassRequestMapper.toEntity(request);
        classRequestValidator.validateAddRequest(exchangeClassRequest);
        exchangeClassRequestService.add(exchangeClassRequest); // throw exception if failed

        ResponseDTO<String> response = new ResponseDTO<>(true, "request added successfully", "no error", "no data");
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PatchMapping("/id/{id}")
    public ResponseEntity<ResponseDTO<ExchangeClassRequestResponseDTO>> update(
            @PathVariable int id,
            @RequestBody UpdateExchangeClassRequestDTO request) {

        ExchangeClassRequest exchangeClassRequest = exchangeClassRequestService.findById(id);

        exchangeClassRequest.setDesiredClassCode(request.getDesiredClassCode());
        classRequestValidator.validateUpdateRequest(exchangeClassRequest);

        ExchangeClassRequestResponseDTO updated =
                exchangeClassRequestMapper.toDto(exchangeClassRequestService.update(exchangeClassRequest));

        ResponseDTO<ExchangeClassRequestResponseDTO> response =
                new ResponseDTO<>(true, "request updated successfully", "no error", updated);

        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @DeleteMapping("/id/{id}")
    public ResponseEntity<ResponseDTO<String>> delete(@PathVariable int id) {

        ExchangeClassRequest  request = exchangeClassRequestService.findById(id);
        exchangeClassRequestService.deleteById(request);

        ResponseDTO<String> response = new ResponseDTO<>(
                true,
                "slot request deleted successfully",
                "no error",
                "no data"
        );

        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @GetMapping("/class_code/{classCode}/page/{page}") /// add pagination to it please pageable page
    public ResponseEntity<ResponseDTO<List<ExchangeClassRequestResponseDTO>>> findByClassCode
            (@PathVariable String classCode ,@PathVariable int page) {

        if(page < 0){
            throw new BaseException("page must be >= 0", HttpStatus.BAD_REQUEST);
        }

        List<ExchangeClassRequestResponseDTO> data = exchangeClassRequestMapper.
                toDtoList(exchangeClassRequestService.findByClassCode(classCode,page));

        if(data.isEmpty()){
            throw new BaseException("no exchange class request found", HttpStatus.NOT_FOUND);
        }

        ResponseDTO<List<ExchangeClassRequestResponseDTO>> response =
                new ResponseDTO<>(true, "request found successfully", "no error", data);

        return ResponseEntity.ok(response);
    }

    @GetMapping("/slot/{slot}/page/{page}") /// add pagination to it please pageable page
    public ResponseEntity<ResponseDTO<List<ExchangeClassRequestResponseDTO>>> findBySlot
            (@PathVariable String slot ,@PathVariable int page) {

        if(page < 0){
            throw new BaseException("page must be >= 0", HttpStatus.BAD_REQUEST);
        }

        List<ExchangeClassRequestResponseDTO> data = exchangeClassRequestMapper.
                toDtoList(exchangeClassRequestService.findBySlot(slot, page));

        if(data.isEmpty()){
            throw new BaseException("no class request found", HttpStatus.NOT_FOUND);
        }

        ResponseDTO<List<ExchangeClassRequestResponseDTO>> response =
                new ResponseDTO<>(true, "request found successfully", "no error", data);

        return ResponseEntity.ok(response);
    }

    @GetMapping("/student_code/{studentCode}")
    public ResponseEntity<ResponseDTO<ExchangeClassRequestResponseDTO>> findByStudentCode(
            @PathVariable String studentCode
            ) {

        ExchangeClassRequestResponseDTO data = exchangeClassRequestMapper
                .toDto(exchangeClassRequestService.findByStudentCode(studentCode));

        ResponseDTO<ExchangeClassRequestResponseDTO> response =
                new ResponseDTO<>(true, "request found successfully", "no error", data);

        return ResponseEntity.ok(response);
    }


    @GetMapping("/id/{id}") ///  for testing
    public ExchangeClassRequestResponseDTO findById(@PathVariable int id){
        return exchangeClassRequestMapper.toDto(exchangeClassRequestService.findById(id));
    }


}