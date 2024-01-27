package com.themaestrocode.onlinelearningplatform.api.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import java.util.Properties;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig {

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(11);
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf().disable()
                .authorizeRequests(authorizeRequests ->
                                authorizeRequests
                                        .requestMatchers("/api/v1/**"/*, "/api/v1/registration/**", "/api/v1/courses/**"*/).permitAll()
//                                .requestMatchers("/api/v1/student/profile/**", "/api/v1/student/courses").hasRole(UserRole.STUDENT.toString())
//                                .requestMatchers("/api/v1/creator/profile/**", "/api/v1/creator/courses").hasRole(UserRole.CREATOR.toString())
//                                .requestMatchers("/admin/api/**").hasRole(UserRole.ADMIN.toString())
//                                .requestMatchers("/admin/api/v1/admin/dashboard").authenticated
                        //.anyRequest().authenticated()
                );
        //.formLogin(Customizer.withDefaults());


        return http.build();
    }

    @Bean
    public JavaMailSender javaMailSender() {
        JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
        mailSender.setHost("smtp.gmail.com");
        mailSender.setPort(587);

        mailSender.setUsername("heysamsunga52@gmail.com");
        mailSender.setPassword("rooc fzan qwqy lqkk");

        Properties props = mailSender.getJavaMailProperties();
        props.put("mail.transport.protocol", "smtp");
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.debug", "true");

        return mailSender;
    }
}
