package com.example.cycle_service.infrastructure.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.web.server.SecurityWebFilterChain;

@Configuration
@EnableWebFluxSecurity
public class SecurityConfig {
    @Bean
    public SecurityWebFilterChain springSecurityFilterChain(ServerHttpSecurity httpSecurity){
        return httpSecurity
                //Desactivamos CSRF ya que usamos Token KWT, no sesiones de servidor
                .csrf(csrf -> csrf.disable())
                //Reglas de autorizacion
                .authorizeExchange(exchanges -> exchanges
                // En este servicio, CUALQUIER ruta requiere que el usuario este logueado
                                .anyExchange().authenticated()
                )
                //Configuramos el servicio como un Servidor de Recursos OAuth2(JWT)
                .oauth2ResourceServer(oauth2 -> oauth2
                        .jwt(Customizer.withDefaults())
                )
                .build();
    }
}
