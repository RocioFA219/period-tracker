package com.example.period.tracker.respository;

import com.example.period.tracker.domain.model.User;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

@Repository
public interface UserRepository extends ReactiveCrudRepository<User, Long> {
    // Mono es un "contenedor" para 0 o 1 elemento (asíncrono)
    Mono<User> findByEmail(String email);
    Mono<User> findByPassword(String password);
}
