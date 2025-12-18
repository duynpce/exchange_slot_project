package Main.Config.Security;

import Main.Utility.JwtUtil;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@RequiredArgsConstructor
@Configuration
public class JWTAuthenticationConfig extends OncePerRequestFilter {

    private final JwtUtil jwtUtility;
    private final ApplicationContext context;

    @Override
    protected void doFilterInternal(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain) throws ServletException, IOException
    {

        // Get Authorization from header
        String authHeader = request.getHeader("Authorization");
        String token  = null;
        String username = null;
        final String accessSecretKey = jwtUtility.getAccessSecretKey();

        //check token
        if(authHeader != null && authHeader.startsWith("Bearer")){
            token = authHeader.substring(7);
            username = jwtUtility.extractUsername(token, accessSecretKey);
        }

        if(username != null && SecurityContextHolder.getContext().getAuthentication() == null){
            UserDetails user = context.getBean(UserDetailServiceConfig.class).loadUserByUsername(username);
            boolean validToken = jwtUtility.validateToken(token , user, accessSecretKey);

            // if token is valid , set authentication to SecurityContextHolder
            if(validToken){
                UsernamePasswordAuthenticationToken authToken /// create authentication object --> mean this request is authenticated
                        = new UsernamePasswordAuthenticationToken(user, null, user.getAuthorities()); /// using token --> no password needed
                authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request)); /// add more information to authentication object
                SecurityContextHolder.getContext().setAuthentication(authToken); //set authentication to SecurityContextHolder
                }
        }

        // do the next filter
        filterChain.doFilter(request,response);
    }

}
