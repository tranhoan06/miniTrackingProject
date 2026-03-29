package com.example.miniTrackingProject.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableMethodSecurity(prePostEnabled = true)
public class SecurityConfig {
    // Khi có request thì spring security sẽ đi qua SecurityFilterChain
     /* HttpSecurity httpSecurity:
        Cho phép request nào
        Chặn request nào
        Dùng kiểu auth gì (JWT, Basic, OAuth2...) **/
    @Bean
    public SecurityFilterChain configSecurity(HttpSecurity httpSecurity) {
        httpSecurity
                .csrf(csrf -> csrf.disable())
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers(HttpMethod.POST, "/v1/api/user/create").permitAll()
                        // Mọi request đều phải được xác thực
                        .anyRequest().authenticated())
                .httpBasic(Customizer.withDefaults()); // =>Cấu hình cách loghin: truyền username, password
        return httpSecurity.build();
    }

    @Bean
    public PasswordEncoder PasswordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
