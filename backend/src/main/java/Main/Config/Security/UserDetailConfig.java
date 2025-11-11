package Main.Config.Security;


import Main.Enum.Role;
import Main.Entity.Account;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class UserDetailConfig implements UserDetails {
    private String username;
    private String password;

    @Enumerated(EnumType.STRING)
    private Role role;

    public UserDetailConfig(Account account){
        this.username = account.getUsername();
        this.password = account.getPassword();
        this.role = account.getRole();
    }

    public UserDetailConfig(UserDetails userDetails){
        this.username = userDetails.getUsername();
        this.password = userDetails.getPassword();
        this.role = userDetails.getAuthorities().stream().findFirst()///  take the main role
                .map(GrantedAuthority::getAuthority) /// take authority
                .map(role -> Role.valueOf(role.replace("ROLE_", "")))  ///  eliminate "ROLE_"
                .orElse(Role.USER); /// default role : USER
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority("ROLE_" + role.name())); ///"ROLE_" + role.name() because the form of GrantedAuthority is Role_xxx
        ///because SimpleGrantedAuthority implement GrantedAuthority, role.name() .toString() but safer, because toString may be over_ride
    }

    @Override
    public String getPassword(){
        return  password;
    }

    @Override
    public String getUsername(){
        return username;
    }

    @Override
    public boolean isAccountNonExpired() { return true; }

    @Override
    public boolean isAccountNonLocked() { return true; }

    @Override
    public boolean isCredentialsNonExpired() { return true; }

    @Override
    public boolean isEnabled() { return true; }
}
