package com.cms.clubmanagementapi.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

    @Bean
    public PasswordEncoder passwordEncoder() {
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http
                    // disable CSRF
                    .csrf(AbstractHttpConfigurer::disable)

                    // authorization
                    .authorizeHttpRequests(authorize -> authorize
                            .requestMatchers(("/api/**")).authenticated()     // all links starting with /api must be authenticated
                            .anyRequest().permitAll())                          // any other request is allowed

                    // login method
                    .httpBasic(Customizer.withDefaults())

                    .build();
    }

}
