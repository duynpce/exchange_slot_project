package Main.RestController;

import Main.DTO.ResponseDTO;
import Main.Model.Enity.ExchangeClassRequest;
import Main.Model.Enity.ExchangeSlotRequest;
import Main.Service.ExchangeSlotRequestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/exchangeSlot")
public class ExchangeSlotRestController {

    @Autowired
    ExchangeSlotRequestService exchangeSlotRequestService;


        @PostMapping
        public ResponseEntity<ResponseDTO<String>> add(@RequestBody ExchangeSlotRequest request) {
            boolean success = exchangeSlotRequestService.add(request);

            ResponseDTO<String> responseDTO = new ResponseDTO<>();
            responseDTO.setProcessSuccess(success);

            if (success) {
                responseDTO.setError("no error");
                responseDTO.setMessage("slot request added successfully");
                responseDTO.setData("no data");
                return ResponseEntity.status(HttpStatus.CREATED).body(responseDTO); // 201
            }

            responseDTO.setError("failed");
            responseDTO.setMessage("slot request add failed");
            responseDTO.setData("no data");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(responseDTO); // 400

        }


        @DeleteMapping("/{id}")
        public ResponseEntity<ResponseDTO<String>> delete(@PathVariable int id) {
            boolean success = exchangeSlotRequestService.deleteById(id);

            ResponseDTO<String> responseDTO = new ResponseDTO<>();
            responseDTO.setProcessSuccess(success);

            if (success) {
                responseDTO.setError("no error");
                responseDTO.setMessage("slot request deleted successfully");
                responseDTO.setData("no data");
                return ResponseEntity.status(HttpStatus.NO_CONTENT).build(); // 204 do not have a body --> build
            }

            responseDTO.setError("not found");
            responseDTO.setMessage("slot request not found");
            responseDTO.setData("no data");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(responseDTO); // 404

        }


    // Find by classCode
    @GetMapping("/class/{classCode}")
    public ResponseEntity<ResponseDTO<List<ExchangeSlotRequest>>> findByClassCode(@PathVariable String classCode) {
        List<ExchangeSlotRequest> result = exchangeSlotRequestService.findByClassCode(classCode);

        if (result.isEmpty()) {
             ResponseDTO<List<ExchangeSlotRequest>> response
                    = new ResponseDTO<>( false,
                    "no data found",
                    "no request with that class code : " + classCode,
                     null);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        }

        ResponseDTO<List<ExchangeSlotRequest>> responseDTO = new ResponseDTO<>();
        responseDTO.setProcessSuccess(true);
        responseDTO.setData(result);
        responseDTO.setError("no error");
        responseDTO.setMessage("slot request(s) found successfully");

        return ResponseEntity.ok(responseDTO); // 200
    }

    // Find by subjectCode
    @GetMapping("/subject/{subjectCode}")
    public ResponseEntity<ResponseDTO<List<ExchangeSlotRequest>>> findBySubjectCode(@PathVariable String subjectCode) {
        List<ExchangeSlotRequest> result = exchangeSlotRequestService.findBySubjectCode(subjectCode);

        if (result.isEmpty()) {
            ResponseDTO<List<ExchangeSlotRequest>> response
                    = new ResponseDTO<>( false,
                    "no data found" ,
                    "no request with that subject code : " +subjectCode ,
                     null);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        }

        ResponseDTO<List<ExchangeSlotRequest>> responseDTO = new ResponseDTO<>();
        responseDTO.setProcessSuccess(true);
        responseDTO.setData(result);
        responseDTO.setError("no error");
        responseDTO.setMessage("slot request(s) found successfully");

        return ResponseEntity.ok(responseDTO);
    }

    // Find by subjectCode + classCode
    @GetMapping("/subject/{subjectCode}/class/{classCode}")
    public ResponseEntity<ResponseDTO<List<ExchangeSlotRequest>>> findBySubjectAndClassCode(
            @PathVariable String classCode,
            @PathVariable String subjectCode) {

        List<ExchangeSlotRequest> result =
                exchangeSlotRequestService.findByClassCodeAndSubjectCode(classCode, subjectCode);

        if (result.isEmpty()) {
            ResponseDTO<List<ExchangeSlotRequest>> response
                    = new ResponseDTO<>( false,
                    "no data found",
                    "no request with that class code : " + classCode + " and subject code: " +subjectCode,
                     null);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        }

        ResponseDTO<List<ExchangeSlotRequest>> responseDTO = new ResponseDTO<>();
        responseDTO.setProcessSuccess(true);
        responseDTO.setData(result);
        responseDTO.setError("no error");
        responseDTO.setMessage("slot request(s) found successfully");

        return ResponseEntity.ok(responseDTO);
    }

    // Find by slot
    @GetMapping("/slot/{slot}")
    public ResponseEntity<ResponseDTO<List<ExchangeSlotRequest>>> findBySlot(@PathVariable String slot) {
        List<ExchangeSlotRequest> result = exchangeSlotRequestService.findBySlot(slot);

        if (result.isEmpty()) {
            ResponseDTO<List<ExchangeSlotRequest>> response
                    = new ResponseDTO<>( false,
                    "no data found",
                    "no request with that slot : " + slot,
                    null);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        }

        ResponseDTO<List<ExchangeSlotRequest>> responseDTO = new ResponseDTO<>();
        responseDTO.setProcessSuccess(true);
        responseDTO.setData(result);
        responseDTO.setError("no error");
        responseDTO.setMessage("slot request(s) found successfully");

        return ResponseEntity.ok(responseDTO);
    }
}
