package com.javaguides.todomanagement.config;

import com.javaguides.todomanagement.security.jwt.JwtAuthenticationEntryPoint;
import com.javaguides.todomanagement.security.jwt.JwtAuthenticationFilter;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@AllArgsConstructor
@EnableMethodSecurity
public class SpringSecurityConfig {

    private UserDetailsService userDetailsService;
    private JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;
    private JwtAuthenticationFilter jwtAuthenticationFilter;

    @Bean
    public static PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

        http.csrf(AbstractHttpConfigurer::disable).authorizeRequests((authorize) -> {
            authorize
                    /*.requestMatchers(HttpMethod.POST, "/api/todos/**").hasRole("ADMIN")
                    .requestMatchers(HttpMethod.PUT, "/api/todos/**").hasRole("ADMIN")
                    .requestMatchers(HttpMethod.DELETE, "/api/todos/**").hasRole("ADMIN")
                    .requestMatchers(HttpMethod.GET, "/api/todos/**").hasAnyRole("USER", "ADMIN")
                    .requestMatchers(HttpMethod.PATCH, "/api/todos/**").hasAnyRole("USER", "ADMIN")*/
                    .requestMatchers("/api/auth/**").permitAll()
                    .requestMatchers(HttpMethod.OPTIONS, "/**").permitAll()
            ;
            authorize.anyRequest().authenticated();
        }).httpBasic(Customizer.withDefaults());

        http.exceptionHandling(exception -> exception.authenticationEntryPoint(jwtAuthenticationEntryPoint));
        http.addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) throws Exception {
        return configuration.getAuthenticationManager();
    }

    /*@Bean
    public UserDetailsService userDetailsService() {

        UserDetails admin = User.builder()
                .username("admin")
                .password(passwordEncoder().encode("admin"))
                .roles("ADMIN")
                .build();

        UserDetails user = User.builder()
                .username("user")
                .password(passwordEncoder().encode("user"))
                .roles("USER")
                .build();

        return new InMemoryUserDetailsManager(user, admin);
    }*/
}
