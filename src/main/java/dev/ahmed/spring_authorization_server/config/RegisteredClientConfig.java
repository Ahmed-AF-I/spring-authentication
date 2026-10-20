package dev.ahmed.spring_authorization_server.config;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcOperations;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.core.AuthorizationGrantType;
import org.springframework.security.oauth2.core.ClientAuthenticationMethod;
import org.springframework.security.oauth2.server.authorization.client.JdbcRegisteredClientRepository;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClientRepository;

import java.util.UUID;

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

    @Bean
    public CommandLineRunner registerInternalClient(
            RegisteredClientRepository repository,
            PasswordEncoder clientSecretPasswordEncoder
    ) {
        return _ -> {
            String clientId = "internal-test-client";

            if (repository.findByClientId(clientId) != null) {
                return;
            }

            var client = org.springframework.security.oauth2.server.authorization.client.RegisteredClient
                    .withId(UUID.randomUUID().toString())
                    .clientId(clientId)
                    .clientName("Internal Test Client")
                    .clientAuthenticationMethod(
                            ClientAuthenticationMethod.CLIENT_SECRET_BASIC
                    )
                    .clientSecret(
                            clientSecretPasswordEncoder.encode("change-me")
                    )
                    .authorizationGrantType(
                            AuthorizationGrantType.CLIENT_CREDENTIALS
                    )
                    .scope("system-read")
                    .build();

            repository.save(client);
        };
    }
}
