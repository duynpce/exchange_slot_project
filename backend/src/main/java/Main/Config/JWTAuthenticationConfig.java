package Main.Config;

import Main.Utility.jwtUtil;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationContext;
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

    private final jwtUtil jwtUtility;
    private final ApplicationContext context;



    @Override
    protected void doFilterInternal(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain) throws ServletException, IOException
    {
        String authHeader = request.getHeader("Authorization"); ///take data at the row begin with Authorization
        String token  = null;
        String username = null;

        if(authHeader != null && authHeader.startsWith("Bearer")){
            token = authHeader.substring(7); /// format is bearer token, so 7 is position of token
            username = jwtUtility.extractUsername(token);
        }

        if(username != null && SecurityContextHolder.getContext().getAuthentication() == null){
            UserDetails user = context.getBean(UserDetailServiceConfig.class).loadUserByUsername(username);
            boolean validToken = jwtUtility.validateToken(token , user);

            if(validToken){
                UsernamePasswordAuthenticationToken authToken /// create authentication object --> mean this request is authenticated
                        = new UsernamePasswordAuthenticationToken(user, null, user.getAuthorities()); /// using token --> no password needed
                authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request)); /// add more information to authentication object
                SecurityContextHolder.getContext().setAuthentication(authToken); //set authentication to SecurityContextHolder
                }
        }

        filterChain.doFilter(request,response); /// do the next filter
    }

}
