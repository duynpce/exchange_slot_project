package Main.Exception;

import lombok.Data;
import org.springframework.http.HttpStatus;

@Data
public class UtilityException extends RuntimeException {
    private HttpStatus httpStatus;

    public UtilityException(){}

    public UtilityException(String message, HttpStatus httpStatus){
        super(message);
        this.httpStatus = httpStatus;
    }
}
