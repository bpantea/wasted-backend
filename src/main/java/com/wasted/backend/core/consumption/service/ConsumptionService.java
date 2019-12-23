package com.wasted.backend.core.consumption.service;

import com.wasted.backend.core.consumption.api.dtos.ConsumptionDto;

public interface ConsumptionService {
    void addConsumption(ConsumptionDto consumptionDto);
}
