package com.ricardoemm.user_service.dto;

import lombok.Builder;

@Builder(toBuilder = true)
public record UserRequest(
        String name,
        String surname,
        String email,
        String address,
        boolean alerting,
        double energyAlertingThreshold
) {
}
