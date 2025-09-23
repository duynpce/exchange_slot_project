package Main.Exception;

import org.springframework.http.HttpStatus;

public class ExchangeSlotRequestException extends RuntimeException{
    private HttpStatus httpStatus;


    public ExchangeSlotRequestException(){}

    public ExchangeSlotRequestException(String message, String error, HttpStatus httpStatus){
        super(message);
        this.httpStatus = httpStatus;
    }

    @Override
    public String getMessage() {
        return super.getMessage();
    }

    public void setHttpStatus(HttpStatus httpStatus) {
        this.httpStatus = httpStatus;
    }

    public HttpStatus getHttpStatus() {
        return httpStatus;
    }


}
