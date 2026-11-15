package dev.ahmed.spring_authorization_server.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcOperations;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.server.authorization.client.JdbcRegisteredClientRepository;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClientRepository;

@Configuration
public class RegisteredClientConfig {

    @Bean
    public RegisteredClientRepository registeredClientRepository(
        JdbcOperations jdbcOperations
    ) {
        return new JdbcRegisteredClientRepository(jdbcOperations);
    }

    @Bean
    public PasswordEncoder clientSecretPasswordEncoder() {
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }
}
