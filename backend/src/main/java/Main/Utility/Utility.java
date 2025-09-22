package Main.Utility;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class Utility {

//        @Value("${jws.secret.login}")
        private final String secretKey = "w4Jf9sK2q1Vx8hYp3Zt6uN0rL5bQ2cF7gHjK9LmN0A=";
/// chua xong o day
    private String generateRefreshToken(String userName){
        long expiration = 60 *60 * 1000 * 24 * 7; ///7 day

        String token =Jwts.builder()
                .setSubject(userName) ///  --> represent user's name by this token
                .setIssuedAt(new Date(System.currentTimeMillis())) ///  issued(created) time
                .setExpiration(new Date(System.currentTimeMillis() + expiration)) /// expiration
                .signWith(SignatureAlgorithm.HS256,"secretKey") /// .HS256 --> symmetric key RS256 --> asymmetric
                .compact(); ///return string;
        return token;
    }
    public String getRefreshToken(String userName){
        return generateRefreshToken(userName);
    }

    private String generateAccessToken(String userName){
        long expiration = 60 * 30 * 1000 ; ///30 minute

        String token =Jwts.builder()
                .setSubject(userName) ///  --> represent user's name by this token
                .setIssuedAt(new Date(System.currentTimeMillis())) ///  issued(created) time
                .setExpiration(new Date(System.currentTimeMillis() + expiration)) /// expiration
                .signWith(SignatureAlgorithm.HS256,"secretKey") /// .HS256 --> symmetric key RS256 --> asymmetric
                .compact(); ///return string;
        return token;
    }
    public String getAccessToken(String userName){
        return generateAccessToken(userName);
    }



}
