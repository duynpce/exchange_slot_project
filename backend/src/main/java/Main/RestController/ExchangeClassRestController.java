package Main.RestController;


import Main.DTO.ExchangeClassRequestDTO;
import Main.DTO.ResponseDTO;
import Main.Exception.ExchangeClassRequestException;
import Main.Exception.URLException;
import Main.Model.Enity.Account;
import Main.Model.Enity.ExchangeClassRequest;
import Main.Model.Enity.MajorClass;
import Main.Service.AccountService;
import Main.Service.ExchangeClassRequestService;
import Main.Service.MajorClassService;
import Main.Validator.ExchangeClassRequestValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/exchange_class")
public class ExchangeClassRestController {

    @Autowired
    ExchangeClassRequestService exchangeClassRequestService;

    @Autowired
    ExchangeClassRequestValidator classRequestValidator;

    @PostMapping
    public ResponseEntity<ResponseDTO<String>> add(@RequestBody ExchangeClassRequestDTO request) {

        ExchangeClassRequest exchangeClass = classRequestValidator.validateAddRequest(request);

        boolean addSuccess = exchangeClassRequestService.add(exchangeClass); // throw exception if failed
        if(!addSuccess) {
            throw new ExchangeClassRequestException
                    ("can not add for some internal errors",HttpStatus.INTERNAL_SERVER_ERROR);
        }

        ResponseDTO<String> response = new ResponseDTO<>(true, "request added successfully", "no error", "no data");
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }



    @DeleteMapping("/{id}")
    public ResponseEntity<ResponseDTO<String>> delete(@PathVariable int id) {
        if(id < 0){
            throw new URLException("id must be >= 0", HttpStatus.BAD_REQUEST);
        }

        exchangeClassRequestService.deleteById(id); //throw exception if fail

        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @GetMapping("class_code/{classCode}/page/{page}") /// add pagination to it please pageable page
    public ResponseEntity<ResponseDTO<List<ExchangeClassRequest>>> findByClassCode
            (@PathVariable String classCode ,@PathVariable int page) {

        if(page < 0){
            throw new URLException("page must be >= 0", HttpStatus.BAD_REQUEST);
        }

        List<ExchangeClassRequest> data = exchangeClassRequestService.findByClassCode(classCode,page);

        if(data.isEmpty()){
            throw new ExchangeClassRequestException("no class request found", HttpStatus.NOT_FOUND);
        }

        ResponseDTO<List<ExchangeClassRequest>> response =
                new ResponseDTO<>(true, "request found successfully", "no error", data);

        return ResponseEntity.ok(response);
    }

    @GetMapping("slot/{slot}/page/{page}") /// add pagination to it please pageable page
    public ResponseEntity<ResponseDTO<List<ExchangeClassRequest>>> findBySlot
            (@PathVariable String slot ,@PathVariable int page) {

        if(page < 0){
            throw new URLException("page must be >= 0", HttpStatus.BAD_REQUEST);
        }

        List<ExchangeClassRequest> data = exchangeClassRequestService.findBySlot(slot, page);

        if(data.isEmpty()){
            throw new ExchangeClassRequestException("no class request found", HttpStatus.NOT_FOUND);
        }

        ResponseDTO<List<ExchangeClassRequest>> response =
                new ResponseDTO<>(true, "request found successfully", "no error", data);

        return ResponseEntity.ok(response);
    }


}