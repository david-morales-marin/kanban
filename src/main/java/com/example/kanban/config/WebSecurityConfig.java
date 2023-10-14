package com.example.kanban.config;


import com.example.kanban.filter.JwtRequestFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import springfox.documentation.swagger.web.SecurityConfiguration;
import springfox.documentation.swagger.web.SecurityConfigurationBuilder;

import static org.springframework.security.config.Customizer.withDefaults;

@Configuration
//@EnableWebSecurity
public class WebSecurityConfig {


    @Autowired
    private JwtRequestFilter jwtRequestFilter;

    @Bean
    public SecurityFilterChain web(HttpSecurity http) throws Exception {
        http
                .csrf().disable()
                .authorizeHttpRequests((authorize) -> authorize
                        .requestMatchers("/**").hasRole("ADMIN")
                        .requestMatchers("/v1/login/authenticate", "/swagger-ui/**",
                                "/v3/api-docs/**", "/swagger-resources/**").permitAll()
                        .requestMatchers("/v1/listaTask").hasRole("USER")
                        .requestMatchers(HttpMethod.POST).hasRole("ADMIN")
                        .anyRequest().authenticated()
                )
                .cors(withDefaults())
                .addFilterBefore(jwtRequestFilter, UsernamePasswordAuthenticationFilter.class)
                .sessionManagement((session) -> session
                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                );

        return http.build();
    }

   /* @Bean
    public SecurityConfiguration security() {
        return SecurityConfigurationBuilder.builder()
                .scopeSeparator(",")
                .additionalQueryStringParams(null)
                .useBasicAuthenticationWithAccessCodeGrant(false)
                .build();
    }*/

    @Bean
    PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    AuthenticationManager authenticationManager(AuthenticationConfiguration
                                                        authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

}