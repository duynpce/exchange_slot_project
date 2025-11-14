package Main.Controller;

import Main.DTO.Common.ResponseDTO;
import Main.DTO.MajorClass.CreateMajorClassDTO;
import Main.DTO.MajorClass.UpdateMajorClassDTO;
import Main.Entity.MajorClass;
import Main.Mapper.MajorClassMapper;
import Main.Service.MajorClassService;
import Main.Validator.MajorClassValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin("*")
@RequestMapping("/class")
@RequiredArgsConstructor
public class MajorClassController {

    private final MajorClassMapper majorClassMapper;
    private final MajorClassValidator majorClassValidator;
    private final MajorClassService majorClassService;

    public ResponseEntity<ResponseDTO<String>> add(CreateMajorClassDTO createMajorClassDTO){
        MajorClass majorClass = majorClassMapper.toEntity(createMajorClassDTO);
        majorClassValidator.validateAddRequest(majorClass);
        majorClassService.add(majorClass);

        ResponseDTO<String> response =
                new ResponseDTO<>(true, "MajorClass added successfully", "no error", null);

        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    public ResponseEntity<ResponseDTO<String>> update(UpdateMajorClassDTO updateMajorClassDTO){
        MajorClass majorClass = majorClassMapper.toEntity(updateMajorClassDTO);
        majorClassValidator.validateUpdateRequest(majorClass);
        majorClassService.update(majorClass);

        ResponseDTO<String> response =
                new ResponseDTO<>(true, "MajorClass updated successfully", "no error", null);

        return ResponseEntity.status(HttpStatus.OK).body(response);
    }


}
