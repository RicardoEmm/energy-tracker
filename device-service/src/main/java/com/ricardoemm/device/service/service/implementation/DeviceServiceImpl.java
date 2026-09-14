package com.ricardoemm.device.service.service.implementation;

import com.ricardoemm.device.service.dto.DeviceRequest;
import com.ricardoemm.device.service.dto.DeviceResponse;
import com.ricardoemm.device.service.exception.DeviceNotFoundException;
import com.ricardoemm.device.service.model.Device;
import com.ricardoemm.device.service.repository.DeviceRepository;
import com.ricardoemm.device.service.service.DeviceService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class DeviceServiceImpl implements DeviceService {

    private final DeviceRepository deviceRepository;

    @Override
    public DeviceResponse getById(UUID id) {
        return toResponse(deviceRepository.findById(id).orElseThrow(
                () -> new DeviceNotFoundException("Device not found with id : " + id)
        ));
    }

    @Override
    public List<DeviceResponse> getAll() {
        return deviceRepository.findAll().stream().map(this::toResponse).toList();
    }

    @Override
    public List<DeviceResponse> getAllByUserId(UUID userId) {
        return deviceRepository.findAllByUserId(userId).stream().map(this::toResponse).toList();
    }

    @Override
    public DeviceResponse createDevice(DeviceRequest request) {
        Device device = toEntity(request);
        final Device savedDevice = deviceRepository.save(device);
        return toResponse(savedDevice);
    }

    @Override
    public DeviceResponse updateDevice(UUID id, DeviceRequest request) {
        Device existing = deviceRepository.findById(id).orElseThrow(
                () -> new DeviceNotFoundException("Device not found with id : " + id)
        );

        existing = existing.toBuilder()
                .name(request.name())
                .type(request.type())
                .location(request.location())
                .userId(request.userId())
                .build();

        return toResponse(deviceRepository.save(existing));
    }

    @Override
    public void deleteDevice(UUID id) {
        if (!deviceRepository.existsById(id))
            throw new IllegalArgumentException("Device not found with id: " + id);

        deviceRepository.deleteById(id);
    }

    @Override
    public void deleteDeviceByUserId(UUID userId) {
        deviceRepository.deleteDevicesByUserId(userId);
    }

    private DeviceResponse toResponse(Device device) {
        return DeviceResponse.builder()
                .id(device.getId())
                .name(device.getName())
                .type(device.getType())
                .location(device.getLocation())
                .userId(device.getUserId())
                .build();
    }

    private Device toEntity(DeviceRequest request) {
        return Device.builder()
                .name(request.name())
                .type(request.type())
                .location(request.location())
                .userId(request.userId())
                .build();
    }
}
