package com.ricardoemm.ingestion.service.service.implementation;

import com.ricardoemm.ingestion.service.dto.EnergyUsageRequest;
import com.ricardoemm.ingestion.service.service.IngestionService;
import com.ricardoemm.kafka.event.EnergyUsageEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class IngestionServiceImpl implements IngestionService {

    private final KafkaTemplate<String, EnergyUsageEvent> kafkaTemplate;

    @Override
    public void ingestEnergyUsage(EnergyUsageRequest request) {
        var event = EnergyUsageEvent.builder()
                .deviceId(request.deviceId())
                .energyConsumed(request.energyConsume())
                .timestamp(request.timestamp())
                .build();

        kafkaTemplate.send("energy-usage", event);
        log.info("Ingested energy usage event: {}", event);
    }
}
