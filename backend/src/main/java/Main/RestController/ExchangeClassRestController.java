package Main.RestController;


import Main.DTO.ResponseDTO;
import Main.Exception.ExchangeClassRequestException;
import Main.Model.Enity.ExchangeClassRequest;
import Main.Service.ExchangeClassRequestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/exchange-class")
public class ExchangeClassRestController {

    @Autowired
    ExchangeClassRequestService exchangeClassRequestService;



    @PostMapping
    public ResponseEntity<ResponseDTO<String>> add(@RequestBody ExchangeClassRequest request) {
        boolean addSuccess = exchangeClassRequestService.add(request) ; // throw exception if failed

        if(!addSuccess) {
            throw new ExchangeClassRequestException("can not add for some internal errors",HttpStatus.INTERNAL_SERVER_ERROR);
        }

        ResponseDTO<String> response = new ResponseDTO<>(true, "request added successfully", "no error", "no data");
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ResponseDTO<String>> delete(@PathVariable int id) {
        exchangeClassRequestService.deleteById(id); //throw exception if fail

        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @GetMapping("/{classCode}")
    public ResponseEntity<ResponseDTO<List<ExchangeClassRequest>>> findByClassCode(@PathVariable String classCode) {
        List<ExchangeClassRequest> data = exchangeClassRequestService.findByClassCode(classCode);


        ResponseDTO<List<ExchangeClassRequest>> response =
                new ResponseDTO<>(true, "request found successfully", "no error", data);

        return ResponseEntity.ok(response);
    }


}