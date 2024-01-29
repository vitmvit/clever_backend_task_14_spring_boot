package ru.clevertec.house.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import ru.clevertec.house.config.auth.SecurityFilter;

@Configuration
@EnableWebSecurity
public class AuthConfig {

    @Autowired
    private SecurityFilter securityFilter;

    @Bean
    public WebSecurityCustomizer webSecurityCustomizer() {
        return web -> web.ignoring().requestMatchers(
                "/api/signup", "/api/signin"
        );
    }

    /**
     * Конфигурация цепочки фильтров безопасности.
     *
     * @param httpSecurity HttpSecurity, настройка HTTP-безопасности.
     * @return Цепочка фильтров безопасности.
     * @throws Exception Если произошла ошибка при настройке фильтров безопасности.
     */
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
        return httpSecurity
                .csrf(AbstractHttpConfigurer::disable)
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(authorize -> authorize
                        // open
                        .requestMatchers(HttpMethod.GET, "/api/history/*").permitAll()
                        .requestMatchers(HttpMethod.GET, "/api/persons/*").permitAll()
                        .requestMatchers(HttpMethod.GET, "/api/houses/*").permitAll()
                        // secure
                        .requestMatchers(HttpMethod.PUT, "/api/*").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.POST, "/api/*").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.PATCH, "/api/*").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.DELETE, "/api/*").hasRole("ADMIN")
                        .anyRequest().authenticated())
                .addFilterBefore(securityFilter, UsernamePasswordAuthenticationFilter.class)
                .build();
    }

    /**
     * Настройка менеджера аутентификации.
     *
     * @param authenticationConfiguration Конфигурация аутентификации.
     * @return Менеджер аутентификации.
     * @throws Exception Если произошла ошибка при настройке менеджера аутентификации.
     */
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration)
            throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

    /**
     * Кодировщик паролей.
     *
     * @return Кодировщик паролей.
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
