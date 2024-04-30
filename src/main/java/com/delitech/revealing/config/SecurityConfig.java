package com.delitech.revealing.config;

import com.delitech.revealing.auth.jwt.JwtAuthenticationFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.List;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final JwtAuthenticationFilter jwtAuthenticationFilter;
    private final AuthenticationProvider authProvider;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http.cors(c -> c.configurationSource(request -> corsConfigurationSource().getCorsConfiguration(request)))
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(authRequest ->
                        authRequest
                                .requestMatchers(new AntPathRequestMatcher("/auth/**")).permitAll()
                                .requestMatchers(new AntPathRequestMatcher("/docs/**")).permitAll()
                                .requestMatchers(new AntPathRequestMatcher("/plans/**")).permitAll()
                                .requestMatchers(new AntPathRequestMatcher("/products/**")).permitAll()
                                .requestMatchers(new AntPathRequestMatcher("/terms-conditions")).permitAll()
                                .requestMatchers(new AntPathRequestMatcher("/img/byuuid/**")).permitAll()
                                .requestMatchers(new AntPathRequestMatcher("/payment-methods")).permitAll()
                                .requestMatchers(new AntPathRequestMatcher("/pet-sizes")).permitAll()
                                .requestMatchers(new AntPathRequestMatcher("/pet-colors")).permitAll()
                                .requestMatchers(new AntPathRequestMatcher("/pet-types/**")).permitAll()
                                .requestMatchers(new AntPathRequestMatcher("/food-presentations")).permitAll()
                                .requestMatchers(new AntPathRequestMatcher("/pet-breeds/**")).permitAll()
                                .requestMatchers(new AntPathRequestMatcher("/departments")).permitAll()
                                .requestMatchers(new AntPathRequestMatcher("/frequencies")).permitAll()
                                .requestMatchers(new AntPathRequestMatcher("/municipalities/**")).permitAll()
                                .requestMatchers(new AntPathRequestMatcher("/food-types/**")).permitAll()
                                .requestMatchers(new AntPathRequestMatcher("/v3/api-docs/**")).permitAll()
                                .requestMatchers(new AntPathRequestMatcher("/users", "POST")).permitAll()
                                .requestMatchers(new AntPathRequestMatcher("/pets/public/**")).permitAll()
                                .requestMatchers(new AntPathRequestMatcher("/recover-pass")).permitAll()
                                .requestMatchers(new AntPathRequestMatcher("/actuator/**")).permitAll()
                                .requestMatchers(new AntPathRequestMatcher("/notification-test")).permitAll()
                                .requestMatchers(new AntPathRequestMatcher("/pets/validate-micro/**")).permitAll()
                                .anyRequest().authenticated()
                )
                .sessionManagement(sessionManager ->
                        sessionManager
                                .sessionCreationPolicy(SessionCreationPolicy.STATELESS))

                .authenticationProvider(authProvider)
                .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class)
                .build();
    }

    @Bean
    CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(List.of("*"));
        configuration.setAllowedOriginPatterns(List.of("*"));
        configuration.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE", "OPTIONS", "PATCH", "HEAD"));
        configuration.setAllowedHeaders(List.of("*"));
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }

}
