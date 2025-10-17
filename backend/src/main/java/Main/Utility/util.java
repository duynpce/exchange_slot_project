package Main.Utility;

import Main.Exception.UtilityException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import java.util.Date;
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

    public String getUsername(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if(authentication == null || !authentication.isAuthenticated()){
            throw new UtilityException("hasn't logged in", HttpStatus.UNAUTHORIZED);
        }

        return authentication.getName();
    }

}
