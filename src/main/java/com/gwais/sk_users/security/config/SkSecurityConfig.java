package com.gwais.sk_users.security.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.gwais.sk_users.security.filter.JwtRequestFilter;
import com.gwais.sk_users.service.SkUserDetailsService;

@Configuration
@EnableWebSecurity
public class SkSecurityConfig {

    @SuppressWarnings("unused") // not used here, but used by the framework
	@Autowired
    private SkUserDetailsService userService;
    
    @Autowired
    private final JwtRequestFilter jwtAuthenticationFilter;
    
    
    public SkSecurityConfig(
    		JwtRequestFilter jwtAuthenticationFilter, 
    		SkUserDetailsService myUserDetailsService) 
    {
        this.jwtAuthenticationFilter = jwtAuthenticationFilter;
        this.userService = myUserDetailsService;
    }
    
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
    
    // No need for Authentication Provider, since it's configured by Spring
    
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http
            .csrf(csrf -> csrf.disable()) // Disable CSRF for stateless authentication
            .authorizeHttpRequests(auth -> auth
                .requestMatchers(
                		"/api/auth/login",
                		"/api/auth/apply",
                		"/api/auth/emailVerification").permitAll() // Allow public access only to these endpoints
                .anyRequest()
                .authenticated() // All other requests require authentication
            )
            .sessionManagement(session -> session
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS) // No sessions for stateless JWT auth
            )
            //.authenticationProvider(authenticationProvider)
            .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class) // Add my custom JWT filter
            .build();
    }
}
