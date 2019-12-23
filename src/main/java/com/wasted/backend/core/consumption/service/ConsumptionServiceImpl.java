package com.wasted.backend.core.consumption.service;

import com.wasted.backend.core.consumption.api.dtos.ConsumptionDto;
import com.wasted.backend.core.consumption.domain.Consumption;
import com.wasted.backend.core.consumption.repository.ConsumptionRepository;
import com.wasted.backend.core.consumption.service.converter.ConsumptionConverter;
import com.wasted.backend.core.consumption.validator.ConsumptionValidator;
import com.wasted.backend.shared.entities.ValidationResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ConsumptionServiceImpl implements ConsumptionService {
    private final ConsumptionRepository consumptionRepository;
    private final ConsumptionConverter consumptionConverter;
    private final ConsumptionValidator consumptionValidator;

    @Autowired
    public ConsumptionServiceImpl(final ConsumptionRepository consumptionRepository,
                                  final ConsumptionConverter consumptionConverter,
                                  final ConsumptionValidator consumptionValidator) {
        this.consumptionRepository = consumptionRepository;
        this.consumptionConverter = consumptionConverter;
        this.consumptionValidator = consumptionValidator;
    }

    @Override
    public void addConsumption(ConsumptionDto consumptionDto) {
        ValidationResult validationResult = new ValidationResult();
        consumptionValidator.validate(consumptionDto, validationResult);
        validationResult.rejectIfHasErrors();
        Consumption consumption = consumptionConverter.convert(consumptionDto);
        consumptionRepository.save(consumption);
    }
}
