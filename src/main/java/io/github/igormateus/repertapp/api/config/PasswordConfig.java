package io.github.igormateus.repertapp.api.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class PasswordConfig {
    
    @Bean
    public PasswordEncoder getPasswordEncoder() {
        
        var encoder = new BCryptPasswordEncoder();
        return encoder;
    }
}
