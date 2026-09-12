package com.ricardoemm.user_service.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder(toBuilder = true)
@AllArgsConstructor
public record UserRequest(
        String name,
        String surname,
        String email,
        String address,
        boolean alerting,
        double energyAlertingThreshold
) {
}
