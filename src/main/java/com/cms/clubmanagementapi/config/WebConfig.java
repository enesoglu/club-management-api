package com.cms.clubmanagementapi.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/api/**")                             // API'deki hangi URL'lerin etkileneceği (/api/ ile başlayan tümü)
                .allowedOrigins("http://localhost:3000")                     // Hangi adresten gelen isteklere izin verileceği
                .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS")   // Hangi HTTP metodlarına izin verileceği
                .allowedHeaders("*")                                         // Gelen istekte hangi header'lara izin verileceği
                .allowCredentials(true);
    }
}