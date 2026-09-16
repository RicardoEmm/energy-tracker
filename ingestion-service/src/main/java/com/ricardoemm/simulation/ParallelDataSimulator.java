package com.ricardoemm.simulation;

import com.ricardoemm.ingestion.service.dto.EnergyUsageRequest;
import jakarta.annotation.PreDestroy;
import lombok.RequiredArgsConstructor;
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
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;

@Slf4j
@Component
public class ParallelDataSimulator implements CommandLineRunner {

    private final RestTemplate restTemplate = new RestTemplate();

    @Value("${simulation.parallel-threads}")
    private int parallelThreads;

    @Value("${simulation.request-per-interval}")
    private int requestPerInterval;

    @Value("${simulation.endpoint}")
    private String ingestionEndpoint;

    private final ExecutorService executorService;

    public ParallelDataSimulator() {
        this.executorService = Executors.newCachedThreadPool();
    }

    @Override
    public void run(String... args) throws Exception {
        log.info("ParallelDataSimulator started ...");
        ((ThreadPoolExecutor) executorService).setCorePoolSize(parallelThreads);
    }

    @Scheduled(fixedRateString = "${simulation.interval-ms}")
    public void sendMockData() {
        int batchSize = requestPerInterval / parallelThreads;
        int remainder = requestPerInterval % parallelThreads;

        for (int i = 0; i < parallelThreads; i++) {
            int requestForThreads = batchSize + (i < remainder ? 1 : 0);
            executorService.submit(() -> {
                for (int j = 0; j < requestForThreads; j++) {
                    EnergyUsageRequest request = EnergyUsageRequest.builder()
                            .deviceId(UUID.randomUUID())
                            .energyConsume(Double.parseDouble("10" + j + ".00"))
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
            });
        }
    }

    @PreDestroy
    public void shutdown() {
        executorService.shutdown();
        log.info("ParallelDataSimulator shut down ...");
    }
}
