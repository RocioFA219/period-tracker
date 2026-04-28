package com.example.period.tracker.domain.dto;

public record UserRequestDTO(String username,String password, String email, Integer averageCycleLength) {
}
