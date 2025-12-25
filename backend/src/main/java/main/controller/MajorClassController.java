package main.controller;

import main.dto.Common.ResponseDTO;
import main.dto.MajorClass.CreateMajorClassDTO;
import main.dto.MajorClass.GetMajorClassDTO;
import main.dto.MajorClass.UpdateMajorClassDTO;
import main.entity.MajorClass;
import main.exception.BaseException;
import main.mapper.MajorClassMapper;
import main.service.MajorClassService;
import main.validator.MajorClassValidator;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/class")
@RequiredArgsConstructor
public class MajorClassController {

    private final MajorClassMapper majorClassMapper;
    private final MajorClassValidator majorClassValidator;
    private final MajorClassService majorClassService;

    @PostMapping
    public ResponseEntity<ResponseDTO<String>> add(@Valid @RequestBody  CreateMajorClassDTO createMajorClassDTO){
        MajorClass majorClass = majorClassMapper.toEntity(createMajorClassDTO);
        majorClassValidator.validateAddRequest(majorClass);
        majorClassService.save(majorClass);

        ResponseDTO<String> response =
                new ResponseDTO<>(true, "MajorClass added successfully", "no error", null);

        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @PatchMapping
    public ResponseEntity<ResponseDTO<String>> update(@Valid @RequestBody  UpdateMajorClassDTO updateMajorClassDTO){
        MajorClass majorClass = majorClassMapper.toEntity(updateMajorClassDTO);
        majorClassValidator.validateUpdateRequest(majorClass);
        majorClassService.save(majorClass);

        ResponseDTO<String> response =
                new ResponseDTO<>(true, "MajorClass updated successfully", "no error", null);

        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @GetMapping("/page/{page}")
    public ResponseEntity<ResponseDTO<List<GetMajorClassDTO>>> findAll(@PathVariable int page) {

        if(page < 0){
            throw new BaseException("page must >= 0" ,HttpStatus.BAD_REQUEST );
        }

        List<GetMajorClassDTO> result =
                majorClassMapper.toDtoList(majorClassService.findAll(page));

        ResponseDTO<List<GetMajorClassDTO>> response =
                new ResponseDTO<>(true, "MajorClasses loaded successfully", "no error", result);

        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

}
