package Main.DTO;

import lombok.Data;
import org.springframework.http.HttpStatus;

@Data
public class ResponseDTO<T>{
    private boolean processSuccess;
    private String error;
    private String message;
    private T data;
    private int httpStatus;

    public ResponseDTO() {
    }

    public ResponseDTO(boolean processSuccess, String error, String message, T data ) {
        this.processSuccess = processSuccess;
        this.error = error;
        this.message = message;
        this.data = data;
    }
//
//    public void isSuccess(String message, HttpStatus httpStatus,T data){
//        setMessage(message);
//        setError("no error");
//        setData(data);
//        setHttpStatus(httpStatus.value());
//        setProcessSuccess(true);
//    }
//
//    public void isFail(String message, String error, HttpStatus httpStatus){
//        setMessage(message);
//        setError(error);
//        setHttpStatus(httpStatus.value());
//        setData(null);
//        setProcessSuccess(false);
//    }
}