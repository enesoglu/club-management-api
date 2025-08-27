package com.cms.clubmanagementapi.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

// TODO method level auth will be implemented
//  @EnableMethodSecurity

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
                        .requestMatchers(HttpMethod.DELETE).hasRole("EXECUTIVE")
                        .requestMatchers(HttpMethod.POST, "/api/terms/create-term").hasAuthority("PRESIDENT")
                        .requestMatchers("/api/**").authenticated()
                        .anyRequest().permitAll())

                // login method
                .httpBasic(Customizer.withDefaults())

                .build();
    }
}