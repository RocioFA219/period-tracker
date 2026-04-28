package com.example.cycle_service.infrastructure.handler;

import com.example.cycle_service.domain.model.dto.CycleRequestDTO;
import com.example.cycle_service.domain.model.dto.CycleResponseDTO;
import com.example.cycle_service.domain.respository.CycleRespository;
import com.example.cycle_service.domain.service.CycleService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;


@Component
@RequiredArgsConstructor
public class CycleHandler {
    private final CycleRespository cycleRespository;
    private final CycleService cycleService;

    public Mono<ServerResponse> buscarCicloPorId(ServerRequest serverRequest){
        String userIdStr = serverRequest.pathVariable("userId");
        Long userId = Long.parseLong(userIdStr);
        return ServerResponse.ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(cycleService.obtenerCiclosPorUsuario(userId), CycleResponseDTO.class);
    }
    public Mono<ServerResponse> nuevoCiclo(ServerRequest serverRequest){
        return serverRequest.bodyToMono(CycleRequestDTO.class)
                .flatMap(dto -> cycleService.registrarNuevoCiclo(dto.userId(), dto.startDate()))
                .flatMap(cicloGuardado -> ServerResponse.status(HttpStatus.CREATED)
                        .contentType(MediaType.APPLICATION_JSON)
                        .bodyValue(cicloGuardado));

    }
}
