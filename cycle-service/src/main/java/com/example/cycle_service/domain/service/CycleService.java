package com.example.cycle_service.domain.service;

import com.example.cycle_service.domain.model.Cycle;
import com.example.cycle_service.domain.model.dto.CycleResponseDTO;
import com.example.cycle_service.domain.model.enums.CycleEnum;
import com.example.cycle_service.domain.respository.CycleRespository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.mongodb.core.ReactiveUpdateOperation;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.LocalDate;

@Service
@RequiredArgsConstructor
public class CycleService {
    private final CycleRespository cycleRespository;
    private final ReactiveUpdateOperation reactiveUpdateOperation;

    public Mono<Cycle> createInitialCycle(Long userId, Integer avgLenght, String username) {
        LocalDate start = LocalDate.now();
        //Logica : fecha de inicio + duracion media = proxima regla

        LocalDate prediction = start.plusDays(avgLenght);

        Cycle cycle = Cycle.builder()
                .userId(userId)
                .averageCycleLength(avgLenght)
                .startDate(start)
                .userName(username)
                .expectedNextDate(prediction) // Guardamos la predicción
                .status(CycleEnum.INITIALIZED)
                .build();
        return cycleRespository.save(cycle);
    }

    public Flux<CycleResponseDTO> obtenerCiclosPorUsuario(Long userId) {
        return cycleRespository.findByUserId(userId)
                .map(cycle -> new CycleResponseDTO(
                        cycle.userId(),
                        cycle.userName(),
                        cycle.startDate(),
                        cycle.averageCycleLength(),
                        cycle.expectedNextDate(),
                        cycle.status()
                ));
    }

    public Mono<Cycle> registrarNuevoCiclo(Long userId, LocalDate nuevaFecha) {
        if (nuevaFecha.isAfter(LocalDate.now())) {
            return Mono.error(new IllegalArgumentException("No se puede registrar en el futuro"));
        }
        //Buscamos el ultimo ciclo para copiar los datos fijos
        return cycleRespository.findAllByUserId(userId)
                .filter(ciclo -> ciclo.getStatus() != CycleEnum.CLOSED)
                .flatMap(cicloAbierto -> {
                    cicloAbierto.setStatus(CycleEnum.CLOSED);
                    return cycleRespository.save(cicloAbierto);
                })
                .collectList()
                .then(cycleRespository.findFirstByUserIdOrderByStartDateDesc(userId)
                        .flatMap(ultimoCiclo -> {
                            LocalDate nuevaPrediccion = nuevaFecha.plusDays(ultimoCiclo.getAverageCycleLength());
                            Cycle nuevo = Cycle.builder()
                                    .userId(userId)
                                    .userName(ultimoCiclo.getUserName())
                                    .averageCycleLength(ultimoCiclo.getAverageCycleLength())
                                    .startDate(nuevaFecha)
                                    .expectedNextDate(nuevaPrediccion)
                                    .status(CycleEnum.ACTIVE)
                                    .build();
                            return cycleRespository.save(nuevo);
                        })
                );
    }
}
