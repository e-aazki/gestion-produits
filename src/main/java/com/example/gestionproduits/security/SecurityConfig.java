package com.example.gestionproduits.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.HeadersConfigurer;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    // ============================================================
    // ETAPE 5 : desactiver la securite par defaut de Spring Security
    // (a n'utiliser que temporairement, le temps de developper les vues)
    // ------------------------------------------------------------
    // @Bean
    // public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
    //     http.authorizeHttpRequests(auth -> auth.anyRequest().permitAll());
    //     http.csrf(csrf -> csrf.disable());
    //     http.headers(headers -> headers.frameOptions(HeadersConfigurer.FrameOptionsConfig::disable));
    //     return http.build();
    // }
    // ============================================================

    // ============================================================
    // ETAPE 7 : securisation reelle de l'application
    // ============================================================

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    // Utilisateurs en memoire (a remplacer par une source JDBC/JPA en production)
    @Bean
    public UserDetailsService userDetailsService(PasswordEncoder passwordEncoder) {
        InMemoryUserDetailsManager manager = new InMemoryUserDetailsManager();
        manager.createUser(User.withUsername("user")
                .password(passwordEncoder.encode("1234"))
                .roles("USER")
                .build());
        manager.createUser(User.withUsername("admin")
                .password(passwordEncoder.encode("1234"))
                .roles("USER", "ADMIN")
                .build());
        return manager;
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.authorizeHttpRequests(auth -> auth
                // ressources publiques
                .requestMatchers("/webjars/**", "/css/**", "/js/**", "/h2-console/**").permitAll()
                // suppression et formulaire d'ajout/edition reserves a ADMIN
                .requestMatchers("/delete/**", "/formProduct/**", "/editProduct/**", "/save/**").hasRole("ADMIN")
                // le reste necessite d'etre authentifie (USER ou ADMIN)
                .anyRequest().authenticated()
        );

        http.formLogin(form -> form
                .loginPage("/login")
                .defaultSuccessUrl("/index", true)
                .permitAll()
        );

        http.logout(logout -> logout
                .logoutUrl("/logout")
                .logoutSuccessUrl("/login?logout")
                .permitAll()
        );

        // necessaire pour pouvoir afficher la console H2 dans une frame + tests
        http.csrf(csrf -> csrf.ignoringRequestMatchers("/h2-console/**"));
        http.headers(headers -> headers.frameOptions(HeadersConfigurer.FrameOptionsConfig::disable));

        return http.build();
    }
}
