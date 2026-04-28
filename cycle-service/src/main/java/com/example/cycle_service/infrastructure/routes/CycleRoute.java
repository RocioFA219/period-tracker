package com.example.cycle_service.infrastructure.routes;

import com.example.cycle_service.infrastructure.handler.CycleHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;

import static org.springframework.web.reactive.function.server.RequestPredicates.GET;
import static org.springframework.web.reactive.function.server.RequestPredicates.POST;

@Configuration
public class CycleRoute {
    @Bean
    public RouterFunction<ServerResponse> routes (CycleHandler cycleHandler){
        return RouterFunctions
                .route(GET("/api/cycles/{userId}"),cycleHandler::buscarCicloPorId)
                .andRoute(POST("/api/cycles/register"),cycleHandler::nuevoCiclo);

    }
}
