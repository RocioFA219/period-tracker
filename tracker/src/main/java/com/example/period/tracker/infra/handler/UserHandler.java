package com.example.period.tracker.handler;

import com.example.period.tracker.domain.dto.UserRequestDTO;
import com.example.period.tracker.domain.dto.UserResponseDTO;
import com.example.period.tracker.mapper.UserMapper;
import com.example.period.tracker.producer.UserEventProducer;
import com.example.period.tracker.respository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.rsocket.RSocketProperties;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;


@Component
@RequiredArgsConstructor
public class  UserHandler {
    private final UserRepository userRepository;
    private final UserEventProducer userEventProducer;

    public Mono<ServerResponse> createUser(ServerRequest serverRequest){
        return serverRequest.bodyToMono(UserRequestDTO.class) //Extrae el DTO del sobre HTTP
                .map(UserMapper::toEntity) //Transforma DTO a entidad
                .flatMap(userRepository::save) //Guarda en BD(Asincrono)
                //Usamos el doOnNext para lanzar el evento sin afectar la respuesta HTTP.
                .doOnNext(user -> userEventProducer.emitUserMessage(user.getId(),user.getAverageCycleLength(),user.getUsername()))
                .map(UserMapper::toDto) // Transforma entidad a dto
                .flatMap(dto -> ServerResponse
                        .status(HttpStatus.CREATED)
                .bodyValue(dto)); //Crea el paquete de la respuesta 201
    }
    public Mono<ServerResponse> getAllUsers(ServerRequest serverRequest){
        return ServerResponse.ok()
                .body(userRepository.findAll()
                        .map(UserMapper::toDto), UserResponseDTO.class);
    }
    public Mono<ServerResponse> findByEmail(ServerRequest serverRequest){
        // Extraemos el parametro "email" de la URl.
        String email = serverRequest.queryParam("email")
                .orElseThrow(() ->new IllegalArgumentException("Email obligatorio"));

        return ServerResponse.ok()
                .body(
                        userRepository.findByEmail(email)
                                .map(UserMapper::toDto),
                        UserResponseDTO.class
                );
    }
    public Mono<ServerResponse> findByPassword(ServerRequest serverRequest){
        return serverRequest.bodyToMono(UserRequestDTO.class)
                .flatMap(login-> userRepository.findByPassword(login.password()))
                .map(UserMapper::toDto)
                .flatMap(userDto-> ServerResponse.ok().bodyValue(userDto))
                .switchIfEmpty(ServerResponse.status(HttpStatus.UNAUTHORIZED).build());
    }
}
