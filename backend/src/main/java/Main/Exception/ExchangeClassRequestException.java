package Main.Exception;

import org.springframework.http.HttpStatus;

public class ExchangeClassRequestException extends RuntimeException{
    private HttpStatus httpStatus;


    public ExchangeClassRequestException(){}

    public ExchangeClassRequestException(String message, HttpStatus httpStatus){
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
