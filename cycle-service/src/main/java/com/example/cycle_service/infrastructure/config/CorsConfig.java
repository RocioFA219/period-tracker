package com.example.cycle_service.infrastructure.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.reactive.UrlBasedCorsConfigurationSource;
import org.springframework.web.cors.reactive.CorsWebFilter;


import java.util.Arrays;

@Configuration
public class CorsConfig {
    @Bean
    public CorsWebFilter corsWebFilter() {
        CorsConfiguration corsConfiguration = new CorsConfiguration();
        // Definimos quien puede conectarse
        corsConfiguration.setAllowedOrigins(Arrays.asList("http://localhost:3000","http://localhost:5173"));
        corsConfiguration.setAllowedOrigins(Arrays.asList("http://localhost:3000","http://localhost:5174"));

        //Definir que metodos permitimos
        corsConfiguration.setMaxAge(3600L); // Tiempo que el navegador guarda la configuracion CORS
        corsConfiguration.addAllowedMethod("GET");
        corsConfiguration.addAllowedMethod("POST");
        corsConfiguration.addAllowedMethod("PUT");
        corsConfiguration.addAllowedMethod("DELETE");
        corsConfiguration.addAllowedMethod("OPTIONS"); // Importante para el Preflight

        //Definir que cabeceras permitimos
        // * permite todas, lo cual es comodo para empezar.
        corsConfiguration.addAllowedHeader("*");

        //Permitimos el envio de credenciales ( Cookies, Auth headers)
        corsConfiguration.setAllowCredentials(true);

        // Aplicamos esta configuracion a todas la ruas de nuestra API
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", corsConfiguration);

        return new CorsWebFilter(source);
    }
}
