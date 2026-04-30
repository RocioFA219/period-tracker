package com.example.period.tracker.domain.dto;

public record UserRequestDTO(String username,
                             String firstName,
                             String lastName,
                             String password,
                             String email,
                             Integer averageCycleLength) {
}
