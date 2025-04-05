package com.ems.config;

import com.ems.security.CustomUserDetailsService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.http.HttpMethod;

@Configuration
public class SecurityConfig {

    @Bean
    public UserDetailsService userDetailsService() {
        return new CustomUserDetailsService();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager(UserDetailsService userDetailsService) {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(userDetailsService);
        authProvider.setPasswordEncoder(passwordEncoder());
        return new ProviderManager(authProvider);
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable())
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/auth/**").permitAll()

                        // ADMIN: Full access to everything
                        .requestMatchers("/employees/**", "/roles/**", "/departments/**", "/users/**").hasRole("ADMIN")

                        // MANAGER: Can GET and PUT on employees
                        .requestMatchers(HttpMethod.GET, "/employees/**").hasRole("MANAGER")
                        .requestMatchers(HttpMethod.PUT, "/employees/**").hasRole("MANAGER")

                        // EMPLOYEE: Can POST only on employees
                        .requestMatchers(HttpMethod.POST, "/employees").hasRole("EMPLOYEE")

                        // Any other requests must be authenticated
                        .anyRequest().authenticated()
                )
                .formLogin(form -> form.defaultSuccessUrl("/employees", true))
                .logout(logout -> logout.logoutUrl("/logout"));

        return http.build();
    }
}
