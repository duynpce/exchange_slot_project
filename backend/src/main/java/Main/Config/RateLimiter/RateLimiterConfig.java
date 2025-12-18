package Main.Config.RateLimiter;

import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.time.Duration;
import java.util.Optional;


@Configuration
@RequiredArgsConstructor
public class RateLimiterConfig extends OncePerRequestFilter {
    private static final String MAX_REQUESTS_PER_FIVE_SECOND = "3";
    private final RedisTemplate<String, Object> redisTemplate;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        String clientIp = request.getRemoteAddr();
        redisTemplate.opsForValue().setIfAbsent(clientIp, MAX_REQUESTS_PER_FIVE_SECOND, Duration.ofSeconds(5));
        Long cache = Optional.ofNullable(redisTemplate.opsForValue().decrement(clientIp)).orElse(0L);

        if (cache < 0) {
            response.setStatus(429);
            response.setContentType("application/json;charset=UTF-8");
            response.getWriter().write("Too many requests. Please try again later.");
            return;
        }

        filterChain.doFilter(request, response);
    }
}
