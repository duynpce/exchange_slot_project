package main.utility;

import main.exception.BaseException;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.CacheManager;
import org.springframework.http.HttpStatus;
import org.springframework.lang.Contract;
import org.springframework.stereotype.Component;

import java.util.regex.Pattern;

@Component
@RequiredArgsConstructor
public class Util {
    private final CacheManager cacheManager;

    public boolean validatePassword(String password){
        final String PASSWORD_PATTERN = /// > 8 words, have at least an upper, lower, special char + a number
                "^(?=.*[a-z])(?=.*[A-Z])(?=.*[^a-zA-Z0-9]).{8,}$";

        if (password == null) return false;
        final Pattern pattern = Pattern.compile(PASSWORD_PATTERN);
        return pattern.matcher(password).matches();
    }

    public boolean isEmail(String email) {
        if (email == null) return false;
        String emailRegex =   "^(?=.{1,64}@)[A-Za-z0-9._%+-]+@" + "[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$";
        final Pattern pattern = Pattern.compile(emailRegex);
        return pattern.matcher(email).matches();

    }

    // contracts for static analysis tools
    @Contract("null, _ -> fail")
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

    public void  throwExceptionIfEquals(Object field1, Object field2, String message){
        if(field1.equals(field2)){
            throw new BaseException(message, HttpStatus.BAD_REQUEST);
        }
    }

    public void  throwExceptionIfNotEquals(Object field1, Object field2, String message){
        if(!field1.equals(field2)){
            throw new BaseException(message, HttpStatus.BAD_REQUEST);
        }
    }
}
