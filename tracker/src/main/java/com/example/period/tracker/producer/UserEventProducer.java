package com.example.period.tracker.producer;


import jakarta.validation.constraints.Size;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class UserEventProducer {
    //KafkaTemplate es la herramienta de Spring para enviar mensajes
    //Usamos String para la clave y String para el valor por ahora.
    private final KafkaTemplate<String, String> kafkaTemplate;

    public static final String TOPIC = "user-events";

    public void emitUserMessage(Long userId, Integer averageCycleLength, String username){
        String message = "USER_CREATED:" + userId + ":" + averageCycleLength + ":" + username;

        //Enviamos el mensaje al topic "user-events"
        kafkaTemplate.send(TOPIC, message)
                .whenComplete((result,ex) -> {
                    if(ex == null){
                        log.info("MENSAJE ENVIADO A KAFKA: {}" , message);
                    } else {
                        log.error("Error al enviar mensaje {}", ex.getMessage());
                    }
                }
    );
    }
}
