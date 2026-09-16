package com.ricardoemm.ingestion.service.service;

import com.ricardoemm.ingestion.service.dto.EnergyUsageRequest;

public interface IngestionService {
    void ingestEnergyUsage(EnergyUsageRequest request);
}
