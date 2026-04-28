package com.example.cycle_service.domain.respository;

import com.example.cycle_service.domain.model.Cycle;
import com.example.cycle_service.domain.model.dto.CycleRequestDTO;
import com.example.cycle_service.domain.model.dto.CycleResponseDTO;
import com.example.cycle_service.domain.model.enums.CycleEnum;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Repository
public interface CycleRespository extends ReactiveMongoRepository<Cycle, String> {
    // Se usa Flux porque un usuario podria tener varios ciclos guardados.
    Flux<CycleResponseDTO> findByUserId (Long userId);
    Mono<Cycle> findFirstByUserIdOrderByStartDateDesc (Long userId);
    Flux<Cycle> findAllByUserId(Long userId);
}
