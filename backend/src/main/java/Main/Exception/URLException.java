package Main.Exception;

import org.springframework.http.HttpStatus;

public class URLException extends RuntimeException{
    private HttpStatus httpStatus;


    public URLException(){}

    public URLException(String message, HttpStatus httpStatus){
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
