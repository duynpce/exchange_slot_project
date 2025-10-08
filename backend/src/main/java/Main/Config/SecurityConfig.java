package Main.Config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.Customizer;
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
import org.springframework.web.bind.annotation.RequestParam;


@EnableWebSecurity
@Configuration
@RequiredArgsConstructor
public class SecurityConfig {

    private final JWTAuthenticationConfig JWTauthenticationConfig;

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

        httpSecurity.csrf(AbstractHttpConfigurer::disable)/// csrf -> csrf.disable
                .sessionManagement
                        (session -> session.
                                        sessionCreationPolicy(SessionCreationPolicy.STATELESS)) ///disable session (default authentication of spring)
                .authorizeHttpRequests(auth -> auth
                .requestMatchers("/login","/register","test/*/*").permitAll()
                .anyRequest().authenticated()
        )
        .logout(LogoutConfigurer::permitAll)//login -> permitAll()
        .addFilterBefore(JWTauthenticationConfig, UsernamePasswordAuthenticationFilter.class );

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