package com.faltauno.faltauno.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    // 1. Aquí creamos la "máquina de encriptar" contraseñas (BCrypt)
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    // 2. Le decimos a Spring que de momento deje TODO abierto para no romper la web.
    // ¡El bloqueo del Panel Admin lo haremos en el próximo paso!
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable()) // Desactivamos esto para que nos deje hacer POST (como crear alertas o pistas)
                .cors(cors -> {}) // Le decimos que respete tu CorsConfig de siempre
                .authorizeHttpRequests(auth -> auth
                        .anyRequest().permitAll() // Dejamos pasar todo de momento
                );
        return http.build();
    }
}