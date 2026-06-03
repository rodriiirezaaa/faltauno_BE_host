package com.faltauno.faltauno.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class CorsConfig {

    @Bean
    public WebMvcConfigurer corsConfigurer() {
        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                registry.addMapping("/**") // Aplica a todas las rutas (/api/usuarios, /api/pistas, etc.)
                        .allowedOriginPatterns("*") // Permite cualquier origen (localhost:5500, 127.0.0.1, etc.)
                        .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS") // Permite todos los métodos
                        .allowedHeaders("*") // Permite cualquier cabecera
                        .allowCredentials(true); // Permite enviar credenciales
            }
        };
    }
}