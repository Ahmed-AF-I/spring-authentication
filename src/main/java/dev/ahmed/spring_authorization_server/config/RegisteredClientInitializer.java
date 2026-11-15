package dev.ahmed.spring_authorization_server.config;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.core.AuthorizationGrantType;
import org.springframework.security.oauth2.core.ClientAuthenticationMethod;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClient;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClientRepository;
import org.springframework.security.oauth2.server.authorization.settings.OAuth2TokenFormat;
import org.springframework.security.oauth2.server.authorization.settings.TokenSettings;

import java.util.UUID;

@Configuration
@RequiredArgsConstructor
public class RegisteredClientInitializer {

    private final RegisteredClientRepository repository;
    private final PasswordEncoder clientSecretPasswordEncoder;

    @Bean
    public CommandLineRunner registerInternalClient() {
        return _ -> {

            String clientId = "internal-test-client";
            RegisteredClient existing = repository.findByClientId(clientId);

            if ( existing != null) {
                RegisteredClient updated = RegisteredClient.from(existing)
                        .tokenSettings(TokenSettings.builder()
                                .accessTokenFormat(OAuth2TokenFormat.REFERENCE)
                                .build()
                        )
                        .build();
                repository.save(updated);
                return;
            }

            var client = RegisteredClient
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
                    .tokenSettings(
                            TokenSettings.builder()
                                    .accessTokenFormat(OAuth2TokenFormat.REFERENCE)
                                    .build()
                    )
                    .scope("system-read")
                    .build();

            repository.save(client);
        };
    }
}
