package com.example.period.tracker.infra.handler;

import com.example.period.tracker.domain.dto.ErrorResponse;
import com.example.period.tracker.domain.exception.TrackerException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;

@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(TrackerException.class)
    public Mono<ResponseEntity<ErrorResponse>> handleTrackerException(TrackerException ex) {
        //Mapeo logico de codigos de error a estados HTTP
        HttpStatus status = switch (ex.getErrorCode()) {
            case "USER_NOT_FOUND" -> HttpStatus.NOT_FOUND;
            case "EMAIL_DUPLICATED" -> HttpStatus.CONFLICT;
            default -> HttpStatus.BAD_REQUEST;
        };
        return Mono.just(ResponseEntity.status(status)
                .body(
                        new ErrorResponse(ex.getErrorCode(), ex.getMessage(), LocalDateTime.now())));
    }

    @ExceptionHandler(Exception.class)
    public Mono<ResponseEntity<ErrorResponse>> handleGenericException(Exception ex) {
        // Aquí podrías añadir un log para el desarrollador
        return Mono.just(ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(new ErrorResponse("INTERNAL_SERVER_ERROR", "Un error inesperado ha ocurrido.", LocalDateTime.now())));
    }
}
