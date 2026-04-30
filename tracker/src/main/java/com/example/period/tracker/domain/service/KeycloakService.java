package com.example.period.tracker.domain.service;

import com.example.period.tracker.domain.dto.UserRequestDTO;
import com.example.period.tracker.domain.exception.TrackerException;
import jakarta.ws.rs.core.Response;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.keycloak.admin.client.Keycloak;
import org.keycloak.representations.idm.CredentialRepresentation;
import org.keycloak.representations.idm.UserRepresentation;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;
import java.util.Collections;

@Service
@RequiredArgsConstructor
@Slf4j
public class KeycloakService {
    private final Keycloak keycloak;

    public Mono<Void> createUserInKeycloak(UserRequestDTO userRequestDTO) {
        return Mono.fromRunnable(() -> {
                    UserRepresentation user = new UserRepresentation();
                    user.setEnabled(true);
                    user.setUsername(userRequestDTO.username());
                    user.setEmail(userRequestDTO.email());
                    user.setFirstName(userRequestDTO.firstName());
                    user.setLastName(userRequestDTO.lastName());

                    //Configuracion de credenciales de contraseña
                    CredentialRepresentation credential = new CredentialRepresentation();
                    credential.setType(CredentialRepresentation.PASSWORD);
                    credential.setValue(userRequestDTO.password());
                    credential.setTemporary(false);
                    user.setCredentials(Collections.singletonList(credential));

                    Response response = keycloak.realm("period-tracker").users().create(user);

                    if ( response.getStatus() != 201) {
                        log.error("Error al crear usuario en Keycloak. Status {}", response.getStatus());
                        throw new RuntimeException("Error al sincronizar con Keycloak");
                    }
                }).subscribeOn(Schedulers.boundedElastic()).then();
    }
    }

