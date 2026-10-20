package dev.ahmed.spring_authorization_server.config;


import com.nimbusds.jose.jwk.JWKSet;
import com.nimbusds.jose.jwk.RSAKey;
import com.nimbusds.jose.jwk.source.ImmutableJWKSet;
import com.nimbusds.jose.jwk.source.JWKSource;
import com.nimbusds.jose.proc.SecurityContext;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.security.Key;
import java.security.KeyStore;
import java.security.cert.Certificate;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;


@Configuration
public class JwkConfig {

    @Bean
    public JWKSource<SecurityContext> jwkSource(

            @Value("${auth.keystore.path}")
            String keystorePath,

            @Value("${AUTH_KEYSTORE_PASSWORD}")
            String keystorePassword
    ) {
        try {
            KeyStore keyStore = KeyStore.getInstance("PKCS12");

            try (InputStream inputStream = Files
                    .newInputStream(Path.of(keystorePath))
            ) {
                keyStore.load(
                        inputStream,
                        keystorePassword.toCharArray()
                );
            }

            String alias = "auth-signing-key";

            Key key = keyStore.getKey(
                    alias,
                    keystorePassword.toCharArray()
            );

            if (!(key instanceof RSAPrivateKey privateKey)) {
                throw new IllegalStateException(
                        "Keystore entry is not an RSA private key"
                );
            }

            Certificate certificate = keyStore.getCertificate(alias);

            if (certificate == null ||
                    !(certificate.getPublicKey() instanceof RSAPublicKey publicKey)) {
                throw new IllegalStateException(
                        "Keystore entry has no RSA public key"
                );
            }

            RSAKey rsaKey = new RSAKey.Builder(publicKey)
                    .privateKey(privateKey)
                    .keyIDFromThumbprint()
                    .build();

            return new ImmutableJWKSet<>(
                    new JWKSet(rsaKey)
            );

        } catch (Exception exception) {

            throw new IllegalStateException(
                    "Failed to load RSA signing key from PKCS12 keystore",
                    exception
            );
        }
    }
}
