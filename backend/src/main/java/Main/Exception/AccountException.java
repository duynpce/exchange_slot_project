package Main.Exception;

import org.springframework.http.HttpStatus;

public class AccountException extends RuntimeException{
    private HttpStatus httpStatus;


    public AccountException(){}

    public AccountException(String message, HttpStatus httpStatus){
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
