package com.cms.clubmanagementapi.config; // Senin paket adınla aynı olmalı

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

import java.util.Arrays;
import java.util.List;

@Configuration
public class WebConfig {

    @Bean
    public CorsFilter corsFilter() {
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        CorsConfiguration config = new CorsConfiguration();

        // Kimlik bilgileriyle (cookie vb.) isteklere izin ver
        config.setAllowCredentials(true);

        // Sadece Angular uygulamasından gelen isteklere izin ver
        config.setAllowedOrigins(List.of("http://localhost:4200"));

        // İzin verilen tüm HTTP başlıkları
        config.setAllowedHeaders(List.of("*"));

        // İzin verilen tüm HTTP metotları (GET, POST, vb.)
        config.setAllowedMethods(List.of("*"));

        // Bu yapılandırmayı /api/ ile başlayan tüm yollar için kaydet
        source.registerCorsConfiguration("/api/**", config);

        return new CorsFilter(source);
    }
}
