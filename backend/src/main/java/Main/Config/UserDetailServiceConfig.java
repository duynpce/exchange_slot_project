package Main.Config;

import Main.Model.Enity.Account;
import Main.Service.AccountService;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

@Configuration
public class UserDetailServiceConfig implements UserDetailsService {

    private final AccountService accountService;

    public UserDetailServiceConfig(AccountService accountService){
        this.accountService = accountService; ///constructor injection
    }

    @Override
    public UserDetails loadUserByUsername (String username) {
        Account account = accountService.findByUserName(username);
        if (account == null) {
            throw new UsernameNotFoundException("user not found");
        }

        return User.builder()
                .username(account.getUsername())
                .password(account.getPassword())
                .roles(account.getRole().name())
                .build();
    }

}

//component in spring security
/// PasswordEncoder(encrypt the password)
/// token generation (using jwt)
/// filterChain(decided which page need which authentication to access)
/// UserDetailService (require from DB  and send to authentication the very user's information )
/// SecurityContextHolder(hold the authentication of the user in ThreadLocal)
/// threadLocal --> similar to connection in hikari connection pool , disappear right after user disconnected
/// AuthencationProvider (authenticate user)
/// AuthencationEntryPoint (use to deny access from unauthenticated user to a needing authenticated endPoint)
/// AuthencationManager (set up userDetail and passwordEncoder, AuthencationProvider, AuthencationEntryPoint)

//my security design
///  --> encode password
///login(authentication) --> query DB to take the password or token and give it to userDetail

/// if has no token or expired token or invalid token
/// -->hash the login password as the algorithms as the password in DB
/// --> compare them
/// --> login failed --> login again
/// login success --> return token --> save token in cookies and DB

///  if has valid token
/// login success --> return new token and update token in DB
