package dev.ahmed.spring_authorization_server.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcOperations;
import org.springframework.security.oauth2.server.authorization.JdbcOAuth2AuthorizationService;
import org.springframework.security.oauth2.server.authorization.OAuth2AuthorizationService;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClientRepository;

@Configuration
public class AuthorizationServiceConfig {

    @Bean
    public OAuth2AuthorizationService auth2AuthorizationService(
            JdbcOperations jdbcOperations,
            RegisteredClientRepository registeredClientRepository
    ) {
        return new JdbcOAuth2AuthorizationService(
                jdbcOperations,
                registeredClientRepository
        );
    }
}
