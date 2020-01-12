package com.wasted.backend.core.consumption.service;

import com.wasted.backend.core.consumption.api.dtos.ConsumptionDto;
import com.wasted.backend.core.consumption.api.dtos.StatsDto;
import com.wasted.backend.core.user.api.exceptions.UserNotFoundException;

public interface ConsumptionService {
    void addConsumption(ConsumptionDto consumptionDto);
    StatsDto getStatsForUser(String userID) throws UserNotFoundException;
}
