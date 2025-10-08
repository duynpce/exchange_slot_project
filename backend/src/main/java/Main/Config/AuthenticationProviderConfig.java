//
//
///  note this one is for simple configuration
//
//
//package Main.Config;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.security.authentication.AuthenticationProvider;
//import org.springframework.security.authentication.BadCredentialsException;
//import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
//import org.springframework.security.core.Authentication;
//import org.springframework.security.core.AuthenticationException;
//import org.springframework.security.core.userdetails.UserDetails;
//import org.springframework.security.crypto.password.PasswordEncoder;
//
//public class AuthenticationProviderConfig implements AuthenticationProvider {
//
//    private final UserDetailConfig userDetailConfig;
//    private final PasswordEncoder passwordEncoder;
//
//    public AuthenticationProviderConfig(UserDetailConfig userDetailConfig, PasswordEncoder passwordEncoder){
//        this.userDetailConfig = userDetailConfig;
//        this.passwordEncoder = passwordEncoder;
//    }
//
//    @Override
//    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
//        String username = authentication.getName();
//        String password = authentication.getCredentials().toString();
//
//        UserDetails user = userDetailConfig.loadUserByUsername(username);
//        if(user == null){
//            throw new BadCredentialsException("no user found");
//        }
//
//        if(!passwordEncoder.matches(password,user.getPassword())){ /// read encrypted password from user --> take the hash algorithms --> use it to raw password --> compare
//            throw new BadCredentialsException("wrong password");
//        }
//
//        return new UsernamePasswordAuthenticationToken(user, password, user.getAuthorities());
//    }
//
//    @Override
//    public boolean supports(Class<?> authentication) {
//        return UsernamePasswordAuthenticationToken.class.isAssignableFrom(authentication);
//    }
//}
