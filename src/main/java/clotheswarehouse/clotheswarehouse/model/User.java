package clotheswarehouse.clotheswarehouse.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@Entity(name = "users")
@NoArgsConstructor(access = AccessLevel.PRIVATE, force = true)
public class User implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String username;
    private String password;
    private String roleO;
    private String token;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        generateTokenForRole(roleO);
        if (roleO.equals("admin"))
            return List.of(new SimpleGrantedAuthority("ROLE_USER"), new SimpleGrantedAuthority("ROLE_ADMIN"));
        else if (roleO.equals("warehouse"))
            return List.of(new SimpleGrantedAuthority("ROLE_USER"), new SimpleGrantedAuthority("ROLE_WAREHOUSE"));
        return List.of(new SimpleGrantedAuthority("ROLE_USER"));
    }

    // Method to generate token based on role
    private void generateTokenForRole(String role) {
        if (role.equals("admin")) {
            this.token = "abc123";
        } else {
            this.token = "Faketoken"; // Or set to a default value for non-admin roles
        }// Handle other roles if needed
        // Log the token value for debugging
        System.out.println("Generated Token: " + this.token);
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

}
