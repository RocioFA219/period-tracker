package com.example.period.tracker.domain.model;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

@Table(name = "users")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class User {
    @Id
    private Long id;
    private String username;
    private String password;
    private String email;
    private Integer averageCycleLength; // Duración media del ciclo (ej: 28 días)
}
