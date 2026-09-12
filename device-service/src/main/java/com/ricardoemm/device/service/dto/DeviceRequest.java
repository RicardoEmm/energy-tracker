package com.ricardoemm.device.service.dto;

import com.ricardoemm.device.service.model.DeviceType;
import lombok.Builder;
import lombok.Getter;

import java.util.UUID;

@Getter
@Builder(toBuilder = true)
public record DeviceRequest(
        DeviceType type,
        String location,
        UUID userId,
        String name) {
}
