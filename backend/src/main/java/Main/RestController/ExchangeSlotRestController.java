package Main.RestController;

import Main.DTO.ResponseDTO;
import Main.Exception.ExchangeSlotRequestException;
import Main.Exception.URLException;
import Main.Model.Enity.ExchangeClassRequest;
import Main.Model.Enity.ExchangeSlotRequest;
import Main.Service.ExchangeSlotRequestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/exchange_slot")
public class ExchangeSlotRestController {

    @Autowired
    ExchangeSlotRequestService exchangeSlotRequestService;



    @PostMapping
    public ResponseEntity<ResponseDTO<String>> add(@RequestBody ExchangeSlotRequest request) {
        boolean success = exchangeSlotRequestService.add(request);

        if (!success) {
            throw new ExchangeSlotRequestException(
                    "can not add slot request due to internal errors",
                    HttpStatus.INTERNAL_SERVER_ERROR
            );
        }

        ResponseDTO<String> response = new ResponseDTO<>(
                true,
                "slot request added successfully",
                "no error",
                "no data"
        );
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ResponseDTO<String>> delete(@PathVariable int id) {
        if(id < 0){
            throw new URLException("id must be >= 0", HttpStatus.BAD_REQUEST);
        }

         exchangeSlotRequestService.deleteById(id); ///throw exception if not found

        ResponseDTO<String> response = new ResponseDTO<>(
                true,
                "slot request deleted successfully",
                "no error",
                "no data"
        );
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body(response);
    }

    @GetMapping("/class/{classCode}/page/{page}")/// add pagination to it please pageable page
    public ResponseEntity<ResponseDTO<List<ExchangeSlotRequest>>> findByClassCode
            (@PathVariable String classCode,
            @PathVariable int page) {
        if(page < 0){
            throw new URLException("page must be >= 0", HttpStatus.BAD_REQUEST);
        }

        List<ExchangeSlotRequest> result = exchangeSlotRequestService.findByClassCode(classCode, page);

        ResponseDTO<List<ExchangeSlotRequest>> response = new ResponseDTO<>(
                true,
                "slot request(s) found successfully",
                "no error",
                result
        );

        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @GetMapping("/subject/{subjectCode}/page/{page}")/// add pagination to it please pageable page
    public ResponseEntity<ResponseDTO<List<ExchangeSlotRequest>>> findBySubjectCode
            (@PathVariable String subjectCode,
             @PathVariable int page) {
        if(page < 0){
            throw new URLException("page must be >= 0", HttpStatus.BAD_REQUEST);
        }

        List<ExchangeSlotRequest> result = exchangeSlotRequestService.findBySubjectCode(subjectCode,page);

        ResponseDTO<List<ExchangeSlotRequest>> response = new ResponseDTO<>(
                true,
                "slot request(s) found successfully",
                "no error",
                result
        );
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @GetMapping("/subject_code&class_code/")/// add pagination to it please pageable page
    public ResponseEntity<ResponseDTO<List<ExchangeSlotRequest>>> findBySubjectAndClassCode(
            @RequestParam("subjectCode") String subjectCode,
            @RequestParam("classCode") String classCode,
            @RequestParam("page") int page
    ) {
        if(page < 0){
            throw new URLException("page must be >= 0", HttpStatus.BAD_REQUEST);
        }

        List<ExchangeSlotRequest> result =
                exchangeSlotRequestService.findByClassCodeAndSubjectCode(classCode, subjectCode, page);

        ResponseDTO<List<ExchangeSlotRequest>> response = new ResponseDTO<>(
                true,
                "slot request(s) found successfully",
                "no error",
                result
        );
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @GetMapping("/slot/{slot}/page/{page}") /// add pagination to it please pageable page
    public ResponseEntity<ResponseDTO<List<ExchangeSlotRequest>>> findBySlot
            (@PathVariable String slot, @PathVariable int page) {

        if(page < 0){
            throw new URLException("page must be >= 0", HttpStatus.BAD_REQUEST);
        }

        List<ExchangeSlotRequest> result = exchangeSlotRequestService.findBySlot(slot, page);

        ResponseDTO<List<ExchangeSlotRequest>> response = new ResponseDTO<>(
                true,
                "slot request(s) found successfully",
                "no error",
                result
        );
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }
}
