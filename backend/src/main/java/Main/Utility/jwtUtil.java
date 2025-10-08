package Main.Utility;

import Main.Config.UserDetailConfig;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.util.Date;
import java.util.function.Function;

@Component
public class jwtUtil {
    private final String secretKey = "w4Jf9sK2q1Vx8hYp3Zt6uN0rL5bQ2cF7gHjK9LmN0AdAdEW123D1hae0ifADH09i3q";

    private SecretKey getKey(){
        byte [] keyBytes = Decoders.BASE64.decode(secretKey); //encode secretKey to BASE64(bytes) for hash algorithms
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
                .signWith(getKey()) /// kind of algorithms to create key
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
                .signWith(getKey()) /// kind of algorithms to create key
                .compact(); /// == .build()
    }

    public String getAccessToken(UserDetailConfig user){
        return generateAccessToken(user);
    }

    // Extract username from token
    public String extractUsername(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    // Extract expiration date from token
    public Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }

    // Extract a specific claim from token
    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    //Validate token
    public boolean validateToken(String token, UserDetails userDetails) {
        final String extractedUsername = extractUsername(token);
        return (extractedUsername.equals(userDetails.getUsername()) && !isTokenExpired(token));
    }


    // Check if the token is expired
    private boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    // Extract all claims from token
    private Claims extractAllClaims(String token) {
        return Jwts
                .parser()
                .verifyWith(getKey())
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

}
