package com.example.cycle_service.infrastructure;


import com.example.cycle_service.domain.model.Cycle;
import com.example.cycle_service.domain.respository.CycleRespository;
import com.example.cycle_service.domain.service.CycleService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

@Component
@RequiredArgsConstructor
@Slf4j
public class UserEventConsumer {

    private final CycleService cycleService;

    @KafkaListener(topics = "user-events", groupId = "cycle-service-group")
    public void consumeUserCreated(String message) {
        log.info("Mensaje recibido desde Kafa {}", message);

        try {
            //Creamos el array parts dividiendo el mensaje
            String [] parts = message.split(":");
            //Validamos que el mensaje tenga el formato User_created:ID:Duracion
            if(parts.length < 3){
                log.warn("Faltan datos {}", message);
                return;
            }
            //Extraemos los valores usando el array que acabamos de crear
            Long userId = Long.parseLong(parts[1]);
            Integer avgLenght = Integer.parseInt(parts[2]);
            String username = parts[3];

        cycleService.createInitialCycle(userId, avgLenght, username)
                .doOnSuccess(saved -> log.info("Usuario {} con prediccion para el {}",
                username,saved.getExpectedNextDate()))
                .subscribe();

        } catch (Exception e) {
            log.error("❌ Error procesando el mensaje de Kafka: {}", e.getMessage());
        }
    }
}
