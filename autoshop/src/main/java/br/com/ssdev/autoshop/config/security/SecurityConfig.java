package br.com.ssdev.autoshop.config.security;

import br.com.ssdev.autoshop.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Autowired
    private ValidateToken validateToken;

    @Bean
    public SecurityFilterChain filters(HttpSecurity http) throws Exception {
        return http
                .csrf(AbstractHttpConfigurer::disable)
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(authorize -> authorize
                        .requestMatchers(HttpMethod.POST, "/auth/login").permitAll()
                        .requestMatchers(HttpMethod.POST, "/auth/register").permitAll()
                        .requestMatchers(HttpMethod.GET, "/api/service-orders/*").hasAnyRole("MECHANIC","CONSULTANT")
                        .requestMatchers(HttpMethod.POST, "/api/service-orders/*").hasAnyRole("MECHANIC","CONSULTANT")
                        .requestMatchers(HttpMethod.DELETE, "/api/service-orders/*").hasRole("CONSULTANT")
                        .requestMatchers(HttpMethod.PUT, "/api/service-orders/*").hasAnyRole("CONSULTANT","MECHANIC")
                        .requestMatchers(HttpMethod.GET, "/api/vehicles/*").hasAnyRole("CONSULTANT","MECHANIC")
                        .requestMatchers(HttpMethod.POST, "/api/vehicles").hasAnyRole("CONSULTANT","MECHANIC")
                        .requestMatchers(HttpMethod.DELETE, "/api/vehicles/*").hasRole("CONSULTANT")
                        .requestMatchers(HttpMethod.PUT, "/api/vehicles/*").hasAnyRole("CONSULTANT","MECHANIC")
                        .requestMatchers(HttpMethod.GET, "/api/users").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.GET, "/api/users/*").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.DELETE, "/api/users/*").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.PUT, "/api/users/*").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.POST, "/api/addresses").hasRole("CONSULTANT")
                        .requestMatchers(HttpMethod.GET, "/api/addresses/*").hasRole("CONSULTANT")
                        .requestMatchers(HttpMethod.GET, "/api/addresses").hasRole("CONSULTANT")
                        .requestMatchers(HttpMethod.PUT, "/api/addresses/*").hasRole("CONSULTANT")
                        .requestMatchers(HttpMethod.DELETE, "/api/addresses/*").hasRole("CONSULTANT")
                        .anyRequest().authenticated()
                )
                .addFilterBefore(validateToken, UsernamePasswordAuthenticationFilter.class)
                .build();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
