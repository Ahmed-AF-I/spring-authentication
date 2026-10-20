package dev.ahmed.spring_authorization_server.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.oauth2.server.authorization.OAuth2AuthorizationServerConfigurer;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.LoginUrlAuthenticationEntryPoint;

@Configuration
public class SecurityConfig {

    @Bean
    @Order(1)
    public SecurityFilterChain securityFilterChain(
            HttpSecurity http
    ) {

        var auth2AuthorizationEndpointConfigurer = new OAuth2AuthorizationServerConfigurer();

        http
                .securityMatcher(auth2AuthorizationEndpointConfigurer.getEndpointsMatcher())
                .with(auth2AuthorizationEndpointConfigurer, authorizationServer ->
                         authorizationServer
                                .oidc(Customizer.withDefaults())
                )
                .authorizeHttpRequests(authorizeRequests ->
                        authorizeRequests.anyRequest().authenticated()
                )
                .exceptionHandling(exceptions ->
                        exceptions.authenticationEntryPoint(
                                new LoginUrlAuthenticationEntryPoint("/login")
                        )
                );
        return http.build();
    }


    @Bean
    @Order(2)
    public SecurityFilterChain applicationSecurityFilterChain(
            HttpSecurity http
    ) {

        http
                .authorizeHttpRequests(authorize ->
                        authorize.anyRequest().authenticated()
                )
                .formLogin(Customizer.withDefaults());

        return http.build();
    }
}
