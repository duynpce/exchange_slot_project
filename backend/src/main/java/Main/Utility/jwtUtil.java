package Main.Utility;

import Main.Config.Security.UserDetailConfig;
import Main.Exception.BaseException;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.util.Date;
import java.util.function.Function;

@Component
@Getter
public class jwtUtil {
    private final String accessSecretKey = "w4Jf9sK2q1Vx8hYp3Zt6uN0rL5bQ2cF7gHjK9LmN0AdAdEW123D1hae0ifADH09i3q";
    private final String refreshSecretKey = "w73HUS1DJ8jhJ1SA213SU123132910sda1327uei12094USe99IIJda123xajAS12";

    public SecretKey getKey(String key){
        byte [] keyBytes = Decoders.BASE64.decode(key); //encode secretKey to BASE64(bytes) for hash algorithms
        return Keys.hmacShaKeyFor(keyBytes); // use hmac(hash algorithms) to create a Key from BASE 64 of secretKey
        /// hmac include (256H, HS512...)
    }

    private String generateRefreshToken(UserDetailConfig user){
        long expiration = 60 *60 * 1000 * 24 * 7; ///7 day

        return Jwts.builder()
                .claim(user.getUsername(),user.getAuthorities()) //data in payload (FE and hacker can read)
                .subject(user.getUsername())/// subject to create key depend on it
                .issuedAt(new Date(System.currentTimeMillis())) /// set time created (issued)
                .expiration(new Date(System.currentTimeMillis() + expiration)) /// set expiration time
                .signWith(getKey(refreshSecretKey)) /// kind of algorithms to create key
                .compact(); /// == .build()
    }

    public String getRefreshToken(UserDetailConfig user){
        return generateRefreshToken(user);
    }

    private String generateAccessToken(UserDetailConfig user){
        long expiration = 60 * 30 * 1000 ; ///30 minute

        return Jwts.builder()
                .claim(user.getUsername(),user.getAuthorities()) //data in payload
                .subject(user.getUsername())/// subject to create key depend on it
                .issuedAt(new Date(System.currentTimeMillis())) /// set time created (issued)
                .expiration(new Date(System.currentTimeMillis() + expiration)) /// set expiration time
                .signWith(getKey(accessSecretKey)) /// kind of algorithms to create key
                .compact(); /// == .build()
    }

    public String getAccessToken(UserDetailConfig user){
        return generateAccessToken(user);
    }

    // Extract username from token
    public String extractUsername(String token, String key) {
        return extractClaim(token, Claims::getSubject, key);
    }

    // Extract expiration date from token
    public Date extractExpiration(String token, String key) {
        return extractClaim(token, Claims::getExpiration, key);
    }

    // Extract a specific claim from token
    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver, String key) {
        final Claims claims = extractAllClaims(token, key);
        return claimsResolver.apply(claims);
    }

    //Validate token
    public boolean validateToken(String token, UserDetails userDetails, String key) {
        final String extractedUsername = extractUsername(token, key);
        return (extractedUsername.equals(userDetails.getUsername()) && !isTokenExpired(token, key));
    }


    // Check if the token is expired
    private boolean isTokenExpired(String token, String key) {
        return extractExpiration(token, key).before(new Date());
    }

    // Extract all claims from token
    private Claims extractAllClaims(String token,String key) {

        //if key is not access key or refresh key then return null
        if(!key.equals(accessSecretKey) && !key.equals(refreshSecretKey)) {
            return null;
        }

        return Jwts
                .parser()
                .verifyWith(getKey(key))
                .build()
                .parseSignedClaims(token)
                .getPayload();
        /*this is similar to
        return Jwts.parserBuilder()
                .setSigningKey(SECRET_KEY)
                .build()
                .parseClaimsJws(token)
                .getBody();
         */
    }

    // get username from ContextHolder
    public String getUsername(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if(authentication == null || !authentication.isAuthenticated()){
            throw new BaseException("haven't logged in", HttpStatus.UNAUTHORIZED);
        }

        return authentication.getName();
    }
}
