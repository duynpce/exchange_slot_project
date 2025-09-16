package Main.RestController;


import Main.DTO.ResponseDTO;
import Main.Model.Enity.ExchangeClassRequest;
import Main.Service.ExchangeClassRequestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/exchangeClass")
public class ExchangeClassRestController {

    @Autowired
    ExchangeClassRequestService exchangeClassRequestService;



    @GetMapping("/add")
    public ResponseDTO add(ExchangeClassRequest request){
        ResponseDTO responseDTO = new ResponseDTO();
        responseDTO.setProcessSuccess(exchangeClassRequestService.add(request));

        if(responseDTO.isProcessSuccess()){
            responseDTO.setError("no error");
            responseDTO.setMessage("request added successfully");
        }
        else {
            responseDTO.setError("existed request");
            responseDTO.setMessage("if you want to create new request, please delete the old one");
        }

        return  responseDTO;
    }

    @GetMapping("/delete")
    public ResponseDTO delete(ExchangeClassRequest request){
        ResponseDTO responseDTO = new ResponseDTO();
        responseDTO.setProcessSuccess(exchangeClassRequestService.delete(request));

        if(responseDTO.isProcessSuccess()){
            responseDTO.setError("no error");
            responseDTO.setMessage("request deleted successfully");
        }
        else {
            responseDTO.setError("no existed request");
            responseDTO.setMessage("no request with student code: " + request.getStudentCode());
        }

        return  responseDTO;
    }

//    @GetMapping("/find")
//    public

}
