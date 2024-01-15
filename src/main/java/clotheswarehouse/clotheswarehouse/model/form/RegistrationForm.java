package clotheswarehouse.clotheswarehouse.model.form;

import org.springframework.security.crypto.password.PasswordEncoder;

import clotheswarehouse.clotheswarehouse.model.User;

import lombok.Data;

@Data
public class RegistrationForm {
    private String username;
    private String password;
    private String roleO;

    public User toUser(PasswordEncoder passwordEncoder) {
        return User.builder()
                .username(username)
                .password(passwordEncoder.encode(password))
                .roleO(roleO)
                .build();
    }

}
