package com.themaestrocode.onlinelearningplatform.api.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

import static org.springframework.security.config.Customizer.withDefaults;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
//                .authorizeRequests()
//                .requestMatchers("/api/v1/home", "/api/v1/register/**").permitAll()
//                .anyRequest()
//                .authenticated()
//                .and()
//                .httpBasic();
//
                .authorizeRequests(authorizeRequests ->
                        authorizeRequests
                                .requestMatchers("/api/v1/home", "/api/v1/home/register", "/api/v1/home/register/**").permitAll()
                                .requestMatchers("/api/v1/student", "/api/v1/creator").authenticated()
                                .requestMatchers("/api/v1/admin").denyAll()
                                .anyRequest().authenticated()
                )
                //.formLogin(withDefaults())
                .httpBasic(withDefaults());

        return http.build();
    }

    @Bean
    public UserDetailsService userDetailsService() {
        UserDetails stuent = User.builder()
                .username("victor")
                .password(passwordEncoder().encode("1234"))
                .roles("STUDENT")
                .build();

        UserDetails admin = User.builder()
                .username("samuel")
                .password(passwordEncoder().encode("4321"))
                .roles("ADMIN")
                .build();

        return new InMemoryUserDetailsManager(stuent, admin);
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(11);
    }
}