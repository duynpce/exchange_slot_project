package Main.Config;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Configuration
public class AuthenticationEntryPointConfig implements AuthenticationEntryPoint {

    @Override
    public void commence( /// this method called when invalid token or unauthenticated
            HttpServletRequest request,
            HttpServletResponse response,
            AuthenticationException authException) throws IOException, ServletException {

        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED); /// create status
        response.setContentType("application/json;charset=UTF-8"); ///UTF-8 --> a standard character set

        response.getWriter().write("{\n");
        response.getWriter().write("\"error\" : \"status 401 - unauthorized, invalid token or haven't logged in\",\n");
        response.getWriter().write("\"message\" : \"this action need authentication - please login to perform\"\n");
        response.getWriter().write("}"); ///write into json file

        /* json file of  response
        {
        "error" : "status 401 - unauthorized",
         "message" : "this action need authentication - please log  in to perform"
         }
         */
    }
}
