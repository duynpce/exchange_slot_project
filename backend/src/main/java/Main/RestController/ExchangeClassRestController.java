package Main.RestController;


import Main.DTO.ResponseDTO;
import Main.Model.Enity.ExchangeClassRequest;
import Main.Service.ExchangeClassRequestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/exchangeClass")
public class ExchangeClassRestController {

    @Autowired
    ExchangeClassRequestService exchangeClassRequestService;



    @PostMapping
    public ResponseDTO<String> add(@RequestBody ExchangeClassRequest request){
        ResponseDTO<String> responseDTO = new ResponseDTO<>();
        responseDTO.setProcessSuccess(exchangeClassRequestService.add(request));

        if(responseDTO.isProcessSuccess()){
            responseDTO.setError("no error");
            responseDTO.setMessage("request added successfully");
            responseDTO.setData("no data");
        }

        return  responseDTO;
    }

    @DeleteMapping
    public ResponseDTO<String> delete(@RequestBody ExchangeClassRequest request){
        ResponseDTO<String> responseDTO = new ResponseDTO<>();
        responseDTO.setProcessSuccess(exchangeClassRequestService.delete(request));

        if(responseDTO.isProcessSuccess()){
            responseDTO.setData("no data");
            responseDTO.setError("no error");
            responseDTO.setMessage("request deleted successfully");
        }

        return  responseDTO;
    }

    @GetMapping
    public ResponseDTO<List<ExchangeClassRequest>> findByClassCode(@RequestBody String classCode){
        ResponseDTO<List<ExchangeClassRequest>> responseDTO = new ResponseDTO<>();

        responseDTO.setProcessSuccess(true);
        responseDTO.setData(exchangeClassRequestService.findByClassCode(classCode));
        responseDTO.setError("no error");
        responseDTO.setMessage("request found successfully");

        return responseDTO; ////could change this method

    }

}