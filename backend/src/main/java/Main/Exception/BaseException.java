package Main.Exception;

import org.springframework.http.HttpStatus;

public class BaseException extends RuntimeException {
    private HttpStatus httpStatus;


    public BaseException(){}

    public BaseException(String message, HttpStatus httpStatus){
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
