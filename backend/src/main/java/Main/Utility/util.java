package Main.Utility;

import Main.Exception.BaseException;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import java.util.regex.Pattern;

@Component
public class util {

    private static final String PASSWORD_PATTERN = /// > 8 words, have at least a upper, lower, special char + a number
            "^(?=.*[a-z])(?=.*[A-Z])(?=.*[^a-zA-Z0-9]).{8,}$";

    private static final Pattern pattern = Pattern.compile(PASSWORD_PATTERN);

    public boolean validatePassword(String password){
        if (password == null) return false;
        return pattern.matcher(password).matches();
    }


    public void throwExceptionIfNull(Object field, String message){
        if(field == null){
            throw new BaseException(message, HttpStatus.BAD_REQUEST);
        }
    }

    public void throwExceptionIfExists(boolean isExists, String message){
        if(isExists){
            throw new BaseException(message, HttpStatus.CONFLICT);
        }
    }

    public void throwExceptionIfNotExists(boolean isExists, String message){
        if(!isExists){
            throw new BaseException(message, HttpStatus.NOT_FOUND);
        }
    }

}
