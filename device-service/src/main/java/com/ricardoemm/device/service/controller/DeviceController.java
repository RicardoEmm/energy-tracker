package com.ricardoemm.device.service.controller;

import com.ricardoemm.device.service.dto.DeviceRequest;
import com.ricardoemm.device.service.dto.DeviceResponse;
import com.ricardoemm.device.service.service.DeviceService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/devices")
@RequiredArgsConstructor
public class DeviceController {

    private final DeviceService deviceService;


    @GetMapping("/{id}")
    public ResponseEntity<DeviceResponse> findById(@PathVariable("id") final UUID id) {
        return ResponseEntity.ok(deviceService.getById(id));
    }

    @GetMapping
    public ResponseEntity<List<DeviceResponse>> findAll() {
        return ResponseEntity.ok(deviceService.getAll());
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<DeviceResponse>> findAllByUserId(@PathVariable("userId") final UUID userId) {
        return ResponseEntity.ok(deviceService.getAllByUserId(userId));
    }

    @PostMapping
    public ResponseEntity<DeviceResponse> create(@RequestBody final DeviceRequest request) {
        return new ResponseEntity<>(deviceService.createDevice(request), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<String> update(@PathVariable("id") final UUID id, @RequestBody final DeviceRequest request) {
        deviceService.updateDevice(id, request);
        return ResponseEntity.ok("device update successfully");
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteById(@PathVariable("id") final UUID id) {
        deviceService.deleteDevice(id);
        return ResponseEntity.ok("device delete was successfully");
    }

    @DeleteMapping("/user/{userId}")
    public ResponseEntity<String> deleteAllByUserId(@PathVariable("userId") final UUID userId) {
        deviceService.deleteDevice(userId);
        return ResponseEntity.ok("devices were deleted successfully");
    }
}
