package com.example.period.tracker.router;

import com.example.period.tracker.infra.handler.UserHandler;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;
import static org.springframework.web.reactive.function.server.RequestPredicates.GET;
import static org.springframework.web.reactive.function.server.RequestPredicates.POST;

//Aqui definimos las URLS, no se usa requestmapping, usamos bean
@Configuration
public class UserRouter {

    @Bean
    public RouterFunction<ServerResponse> routes (UserHandler userHandler){
        return RouterFunctions
                .route(POST("/api/user"), userHandler::createUser)
                .andRoute(GET("/api/users"), userHandler::getAllUsers)
                .andRoute(GET("/api/user/search"), userHandler::findByEmail);
    }
}
