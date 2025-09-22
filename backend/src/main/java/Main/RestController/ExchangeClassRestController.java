package Main.RestController;


import Main.DTO.ResponseDTO;
import Main.Model.Enity.ExchangeClassRequest;
import Main.Service.ExchangeClassRequestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/exchangeClass")
public class ExchangeClassRestController {

    @Autowired
    ExchangeClassRequestService exchangeClassRequestService;



    @PostMapping
    public ResponseEntity<ResponseDTO<String>> add(@RequestBody ExchangeClassRequest request) {
        boolean success = exchangeClassRequestService.add(request);

        if (success) {
            ResponseDTO<String> response = new ResponseDTO<>(true,"request added successfully",  "no error", "no data");
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        }

            ResponseDTO<String> response = new ResponseDTO<>( false,"failed to add request", "already existed", "no data");
            return ResponseEntity.status(HttpStatus.CONFLICT).body(response);

    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ResponseDTO<String>> delete(@PathVariable int id) {
        boolean DeleteSuccess = exchangeClassRequestService.deleteById(id);

        if (DeleteSuccess) {
            ResponseDTO<String> response = new ResponseDTO<>(true,"no error", "request deleted successfully"  , "no data");
            return ResponseEntity.noContent().build();
        }

            ResponseDTO<String> response = new ResponseDTO<>(false, "not found", "delete failed" , "no data");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);

    }

    @GetMapping("/{classCode}")
    public ResponseEntity<ResponseDTO<List<ExchangeClassRequest>>> findByClassCode(@PathVariable String classCode) {
        List<ExchangeClassRequest> data = exchangeClassRequestService.findByClassCode(classCode);

        if (data.isEmpty()) {
            ResponseDTO<List<ExchangeClassRequest>> response = new ResponseDTO<>( false, "no data found","no request with that class code : " + classCode, null);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        }

        ResponseDTO<List<ExchangeClassRequest>> response = new ResponseDTO<>(true, "no error", "request found successfully", data);
        return ResponseEntity.ok(response); // 200 OK
    }


}