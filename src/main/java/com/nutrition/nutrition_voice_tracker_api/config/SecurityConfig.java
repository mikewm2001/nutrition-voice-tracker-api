package com.nutrition.nutrition_voice_tracker_api.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                // For APIs + Postman testing (we'll re-enable with JWT later if needed)
                .csrf(csrf -> csrf.disable())

                // No sessions (JWT-friendly)
                .sessionManagement(sm -> sm.sessionCreationPolicy(SessionCreationPolicy.STATELESS))

                // Disable default login UI / basic auth prompts
                .httpBasic(Customizer.withDefaults());
        // NOTE: We’ll remove httpBasic later once JWT filter is in

        http
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers(HttpMethod.POST, "/auth/**").permitAll()
                        .anyRequest().authenticated()
                );

        return http.build();
    }
}