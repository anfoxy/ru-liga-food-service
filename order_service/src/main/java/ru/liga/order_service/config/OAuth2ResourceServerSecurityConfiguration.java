package ru.liga.order_service.config;

import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;

/**
 * OAuth конфигурация.
 *
 */
@EnableWebSecurity
public class OAuth2ResourceServerSecurityConfiguration {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .mvcMatcher("/**")
                .authorizeRequests()
                .mvcMatchers("/**")
                .access("hasAuthority('SCOPE_message.read')")
                .and()
                .oauth2ResourceServer()
                .jwt();
        return http.build();
    }

}