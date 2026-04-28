package com.example.cycle_service.domain.model;

import com.example.cycle_service.domain.model.enums.CycleEnum;
import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDate;

@Document(collection = "cycles")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class Cycle {
    @Id
    private String id; // Mongo usa Strings para IDs por defecto (ObjectIds)
    private Long userId;
    private String userName;
    private LocalDate startDate;
    private Integer averageCycleLength;
    private LocalDate expectedNextDate;
    private CycleEnum status; // Ej: "INITIALIZED"
}
