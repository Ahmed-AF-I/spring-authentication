package dev.ahmed.spring_authorization_server.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;

@Configuration
public class UserConfig {

    @Bean
    public UserDetailsService userDetailsService(
            PasswordEncoder clientSecretPasswordEncoder
    ) {
        UserDetails user = User.builder()
                .username("ahmed")
                .password(clientSecretPasswordEncoder.encode("dev_password"))
                .roles("USER")
                .build();
        return new InMemoryUserDetailsManager(user);
    }
}
