package com.example.cycle_service.domain.model.dto;

import java.time.LocalDate;

public record CycleRequestDTO(
        Long userId,
        String userName,
        LocalDate startDate,
        Integer averageCycleLength,
        LocalDate expectedNextDate,
        String status) {
}
