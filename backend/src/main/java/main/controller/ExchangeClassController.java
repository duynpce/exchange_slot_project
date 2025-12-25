package main.controller;


import main.dto.ExchangeClassRequest.CreateExchangeClassRequestDTO;
import main.dto.ExchangeClassRequest.GetExchangeClassRequestDTO;
import main.dto.Common.ResponseDTO;
import main.dto.ExchangeClassRequest.UpdateExchangeClassRequestDTO;
import main.exception.BaseException;
import main.entity.ExchangeClassRequest;
import main.mapper.ExchangeClassRequestMapper;
import main.service.ExchangeClassRequestService;
import main.validator.ExchangeClassRequestValidator;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/exchange_class")
@RequiredArgsConstructor
public class ExchangeClassController {

    private final ExchangeClassRequestService exchangeClassRequestService;

    private final ExchangeClassRequestValidator classRequestValidator;

    private final ExchangeClassRequestMapper exchangeClassRequestMapper;

    @PostMapping
    public ResponseEntity<ResponseDTO<String>> add(@Valid @RequestBody CreateExchangeClassRequestDTO request) {

        ExchangeClassRequest exchangeClassRequest = exchangeClassRequestMapper.toEntity(request);
        classRequestValidator.validateAddRequest(exchangeClassRequest);
        exchangeClassRequestService.add(exchangeClassRequest); // throw exception if failed

        ResponseDTO<String> response = new ResponseDTO<>(true, "request added successfully", "no error", "no data");
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PatchMapping("/id/{id}")
    public ResponseEntity<ResponseDTO<GetExchangeClassRequestDTO>> update(
            @PathVariable int id,
            @Valid @RequestBody UpdateExchangeClassRequestDTO request) {

        ExchangeClassRequest exchangeClassRequest = exchangeClassRequestService.findById(id);

        exchangeClassRequest.setDesiredClassCode(request.getDesiredClassCode());
        classRequestValidator.validateUpdateRequest(exchangeClassRequest);

        GetExchangeClassRequestDTO updated =
                exchangeClassRequestMapper.toDto(exchangeClassRequestService.update(exchangeClassRequest));

        ResponseDTO<GetExchangeClassRequestDTO> response =
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
    public ResponseEntity<ResponseDTO<List<GetExchangeClassRequestDTO>>> findByClassCode
            (@PathVariable String classCode ,@PathVariable int page) {

        if(page < 0){
            throw new BaseException("page must be >= 0", HttpStatus.BAD_REQUEST);
        }

        List<GetExchangeClassRequestDTO> data = exchangeClassRequestMapper.
                toDtoList(exchangeClassRequestService.findByClassCode(classCode,page));

        if(data.isEmpty()){
            throw new BaseException("no exchange class request found", HttpStatus.NOT_FOUND);
        }

        ResponseDTO<List<GetExchangeClassRequestDTO>> response =
                new ResponseDTO<>(true, "request found successfully", "no error", data);

        return ResponseEntity.ok(response);
    }

    @GetMapping("/slot/{slot}/page/{page}") /// add pagination to it please pageable page
    public ResponseEntity<ResponseDTO<List<GetExchangeClassRequestDTO>>> findBySlot
            (@PathVariable String slot ,@PathVariable int page) {

        if(page < 0){
            throw new BaseException("page must be >= 0", HttpStatus.BAD_REQUEST);
        }

        List<GetExchangeClassRequestDTO> data = exchangeClassRequestMapper.
                toDtoList(exchangeClassRequestService.findBySlot(slot, page));

        if(data.isEmpty()){
            throw new BaseException("no class request found", HttpStatus.NOT_FOUND);
        }

        ResponseDTO<List<GetExchangeClassRequestDTO>> response =
                new ResponseDTO<>(true, "request found successfully", "no error", data);

        return ResponseEntity.ok(response);
    }

    @GetMapping("/student_code/{studentCode}")
    public ResponseEntity<ResponseDTO<GetExchangeClassRequestDTO>> findByStudentCode(
            @PathVariable String studentCode
            ) {

        GetExchangeClassRequestDTO data = exchangeClassRequestMapper
                .toDto(exchangeClassRequestService.findByStudentCode(studentCode));

        ResponseDTO<GetExchangeClassRequestDTO> response =
                new ResponseDTO<>(true, "request found successfully", "no error", data);

        return ResponseEntity.ok(response);
    }


    @GetMapping("/id/{id}") ///  for testing
    public GetExchangeClassRequestDTO findById(@PathVariable int id){
        return exchangeClassRequestMapper.toDto(exchangeClassRequestService.findById(id));
    }


}