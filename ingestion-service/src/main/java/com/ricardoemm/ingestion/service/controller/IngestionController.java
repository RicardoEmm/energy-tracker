package com.ricardoemm.ingestion.service.controller;

import com.ricardoemm.ingestion.service.dto.EnergyUsageRequest;
import com.ricardoemm.ingestion.service.service.IngestionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/ingestions")
@RequiredArgsConstructor
public class IngestionController {

    private final IngestionService ingestionService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void ingestData(@RequestBody final EnergyUsageRequest request) {
        ingestionService.ingestEnergyUsage(request);
    }
}
