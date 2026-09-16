package com.ricardoemm.simulation;

import com.ricardoemm.ingestion.service.dto.EnergyUsageRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.time.Instant;
import java.time.ZoneId;
import java.util.UUID;

@Slf4j
@Component
public class ContinuousDataSimulator implements CommandLineRunner {

    private final RestTemplate restTemplate = new RestTemplate();

    @Value("${simulation.request-per-interval}")
    private int requestsPerInterval;

    @Value("${simulation.endpoint}")
    private String ingestionEndpoint;

    @Override
    public void run(String... args) throws Exception {
        log.info("ContinuousDataSimulator started ...");
    }

    @Scheduled(fixedRateString = "${simulation.interval-ms}")
    public void sendMockData() {
        for (int i = 0; i < requestsPerInterval; i++) {
            EnergyUsageRequest request = EnergyUsageRequest.builder()
                    .deviceId(UUID.randomUUID())
                    .energyConsume(Double.parseDouble("10" + i + ".00"))
                    .timestamp(Instant.now().atZone(ZoneId.systemDefault()).toInstant())
                    .build();

            try {
                HttpHeaders headers = new HttpHeaders();
                headers.setContentType(MediaType.APPLICATION_JSON);

                HttpEntity<EnergyUsageRequest> entity = new HttpEntity<>(request, headers);
                restTemplate.postForEntity(ingestionEndpoint, entity, Void.class);
                log.info("Sent mock data {}", request);
            } catch (Exception e) {
                log.error("Failed to send data: {}", e.getMessage());
            }
        }
    }
}
