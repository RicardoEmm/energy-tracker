package com.ricardoemm.device.service.repository;

import com.ricardoemm.device.service.model.Device;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface DeviceRepository extends JpaRepository<Device, UUID> {
    List<Device> findAllByUserId(UUID userId);

    void deleteDevicesByUserId(UUID userId);
}
