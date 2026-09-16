package com.ricardoemm.ingestion.service.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Builder;

import java.time.Instant;
import java.util.UUID;

@Builder(toBuilder = true)
public record EnergyUsageRequest(
        UUID deviceId,
        double energyConsume,
        @JsonFormat(shape = JsonFormat.Shape.STRING)
        Instant timestamp
) {
}
