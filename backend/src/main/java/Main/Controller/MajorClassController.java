package Main.Controller;

import Main.DTO.Common.ResponseDTO;
import Main.DTO.MajorClass.CreateMajorClassDTO;
import Main.DTO.MajorClass.GetMajorClassDTO;
import Main.DTO.MajorClass.UpdateMajorClassDTO;
import Main.Entity.MajorClass;
import Main.Mapper.MajorClassMapper;
import Main.Service.MajorClassService;
import Main.Validator.MajorClassValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin("*")
@RequestMapping("/class")
public class MajorClassController {

    @Autowired
    MajorClassMapper majorClassMapper;

    @Autowired
    MajorClassValidator majorClassValidator;

    @Autowired
    MajorClassService majorClassService;

    @PostMapping
    public ResponseEntity<ResponseDTO<String>> add(CreateMajorClassDTO createMajorClassDTO){
        MajorClass majorClass = majorClassMapper.toEntity(createMajorClassDTO);
        majorClassValidator.validateAddRequest(majorClass);
        majorClassService.add(majorClass);

        ResponseDTO<String> response =
                new ResponseDTO<>(true, "MajorClass added successfully", "no error", null);

        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @PutMapping
    public ResponseEntity<ResponseDTO<String>> update(UpdateMajorClassDTO updateMajorClassDTO){
        MajorClass majorClass = majorClassMapper.toEntity(updateMajorClassDTO);
        majorClassValidator.validateUpdateRequest(majorClass);
        majorClassService.update(majorClass);

        ResponseDTO<String> response =
                new ResponseDTO<>(true, "MajorClass updated successfully", "no error", null);

        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @GetMapping("page/{page}")
    public ResponseEntity<ResponseDTO<List<GetMajorClassDTO>>> findAll(@PathVariable int page) {

        if(page < 0){
            throw new RuntimeException("page must >= 0");
        }

        List<GetMajorClassDTO> result =
                majorClassMapper.toDtoList(majorClassService.findAll(page));

        ResponseDTO<List<GetMajorClassDTO>> response =
                new ResponseDTO<>(true, "MajorClasses loaded successfully", "no error", result);

        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

}
