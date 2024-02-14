package com.portfolio.task_manager;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;

import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests(authorizeRequests -> authorizeRequests
                        // Accès réservé aux utilisateurs avec le rôle ADMIN
                        .requestMatchers("/admin/**").hasRole("ADMIN")
                        // Accès ouvert à la page de connexion et d'inscription
                        .requestMatchers("/login", "/registration").permitAll()
                        // Toutes les autres requêtes nécessitent une authentification
                        .anyRequest().authenticated())

                .formLogin(formLogin -> formLogin // Configuration de la connexion basée sur un formulaire
                        .loginPage("/login") // Page de connexion personnalisée
                        .permitAll() // Accès ouvert à la page de connexion
                )
                .logout(logout -> logout // Configuration de la déconnexion
                        .logoutUrl("/logout")
                        .logoutSuccessUrl("/login"))
                .httpBasic(Customizer.withDefaults()); // Configuration HTTP Basic avec les paramètres par défaut
        return http.build();
    }
}
