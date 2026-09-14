package com.ricardoemm.user_service.dto;

import lombok.Builder;

import java.util.UUID;

@Builder(toBuilder = true)
public record UserResponse(
        UUID id,
        String name,
        String surname,
        String email,
        String address,
        boolean alerting,
        double energyAlertingThreshold
) {
}
