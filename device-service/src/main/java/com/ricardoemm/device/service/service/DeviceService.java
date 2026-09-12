package com.ricardoemm.device.service.service;

import com.ricardoemm.device.service.dto.DeviceRequest;
import com.ricardoemm.device.service.dto.DeviceResponse;

import java.util.List;
import java.util.UUID;

public interface DeviceService {
    DeviceResponse getById(UUID id);
    List<DeviceResponse> getAll();
    List<DeviceResponse> getAllByUserId(UUID userId);
    DeviceResponse createDevice(DeviceRequest request);
    DeviceResponse updateDevice(UUID id, DeviceRequest request);
    void deleteDevice(UUID id);
    void deleteDeviceByUserId(UUID userId);
}
