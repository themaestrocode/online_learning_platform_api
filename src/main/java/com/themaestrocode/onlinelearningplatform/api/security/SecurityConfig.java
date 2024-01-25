package com.themaestrocode.onlinelearningplatform.api.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

import static org.springframework.security.config.Customizer.withDefaults;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Autowired
    private PasswordEncoder passwordEncoder;

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
                .csrf().disable()
                .authorizeRequests(authorizeRequests ->
                        authorizeRequests
                                .requestMatchers("/api/v1/login/**", "/api/v1/register/**").permitAll()
                                .requestMatchers("/api/v1/student/profile/**", "/api/v1/student/courses").hasRole(UserRole.STUDENT.toString())
                                .requestMatchers("/api/v1/creator/profile/**", "/api/v1/creator/courses").hasRole(UserRole.CREATOR.toString())
                                .requestMatchers("/admin/api/**").hasRole(UserRole.ADMIN.toString())
                                .requestMatchers("/admin/api/v1/admin/dashboard").authenticated()
                                .anyRequest().authenticated()
                )
                //.formLogin(withDefaults())
                .httpBasic(withDefaults());

        return http.build();
    }

    @Bean
    public UserDetailsService userDetailsService() {
        UserDetails student1 = User.builder()
                .username("student1")
                .password(passwordEncoder.encode("1111"))
                .roles(UserRole.STUDENT.toString())
                .build();

        UserDetails student2 = User.builder()
                .username("student2")
                .password(passwordEncoder.encode("1111"))
                .roles(UserRole.STUDENT.toString())
                .build();

        UserDetails student3 = User.builder()
                .username("student3")
                .password(passwordEncoder.encode("1111"))
                .roles(UserRole.STUDENT.toString())
                .build();

        UserDetails creator1 = User.builder()
                .username("creator1")
                .password(passwordEncoder.encode("2222"))
                .roles(UserRole.CREATOR.toString())
                .build();

        UserDetails creator2 = User.builder()
                .username("creator2")
                .password(passwordEncoder.encode("2222"))
                .roles(UserRole.CREATOR.toString())
                .build();

        UserDetails admin = User.builder()
                .username("admin")
                .password(passwordEncoder.encode("3333"))
                .roles(UserRole.ADMIN.toString())
                .build();

        return new InMemoryUserDetailsManager(student1, student2, student3, creator1, creator2, admin);
    }

}