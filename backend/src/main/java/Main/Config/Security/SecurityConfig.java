package Main.Config.Security;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.annotation.web.configurers.LogoutConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;

import java.util.List;


@EnableWebSecurity
@Configuration
@RequiredArgsConstructor
public class SecurityConfig {

    private final JWTAuthenticationConfig JWTauthenticationConfig;
    private final AuthenticationEntryPointConfig authenticationEntryPointConfig;
    private final AccessDeniedHandlerConfig accessDeniedHandlerConfig;

    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authManager(
            HttpSecurity http,
            UserDetailServiceConfig userDetailServiceConfig
    ) throws Exception {
        AuthenticationManagerBuilder builder =
               http.getSharedObject(AuthenticationManagerBuilder.class);/// create AuthenticationManagerBuilder

        builder.userDetailsService(userDetailServiceConfig).passwordEncoder(passwordEncoder());

        return builder.build();
    }


    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception { //handle exception later

        httpSecurity.csrf(AbstractHttpConfigurer::disable)/// csrf -> csrf.disable because it is for session not for jwt
                .sessionManagement
                        (session -> session.
                                        sessionCreationPolicy(SessionCreationPolicy.STATELESS)) ///disable session (default authentication of spring)
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/login","/register", "/refresh_access_token", "/hello").permitAll()
                        .requestMatchers("/test-access-denied-handler","/class").hasRole("ADMIN")
                        .anyRequest().authenticated() //logged in --> can access --> do not care about role
                         )
                    .cors(cors -> cors.configurationSource(request -> {
                        /// turn off cors temporary for testing --> config again when deploy --
                        CorsConfiguration corsConfiguration = new CorsConfiguration();
                        corsConfiguration.setAllowedOrigins(List.of("*"));
                        corsConfiguration.setAllowedMethods(List.of("*"));
                        corsConfiguration.setAllowedHeaders(List.of("*"));
                        corsConfiguration.setAllowCredentials(false);
                        return  corsConfiguration;
                    }))
                .logout(LogoutConfigurer::permitAll)//login -> permitAll()
                .addFilterBefore(JWTauthenticationConfig, UsernamePasswordAuthenticationFilter.class )
                .exceptionHandling(handle -> handle /// config exception handler
                        .authenticationEntryPoint(authenticationEntryPointConfig)
                        .accessDeniedHandler(accessDeniedHandlerConfig)
                );


        return httpSecurity.build();
    }
}


///csrf --> spring security default protection
///requestMatchers(url) --> treat url like as you custom
//form login --> login form -->spring security implement --> spring create FE for you
//logout --> log out -->spring security implement
//addFilterBefore(YourCustomFilter, NameOfFilter.class );
    // --> YourCustomFilter must implement or extends an existing filter like OncePerRequestFilter
    //--> NameOfFilter.class --> the filter that you put YourCustomFilter before it
//addFilterAfter --> same but put after
//addFilter --> put the exact position