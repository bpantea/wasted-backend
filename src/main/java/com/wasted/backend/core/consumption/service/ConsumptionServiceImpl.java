package com.wasted.backend.core.consumption.service;

import com.wasted.backend.core.consumption.api.dtos.ConsumptionDto;
import com.wasted.backend.core.consumption.api.dtos.StatsDto;
import com.wasted.backend.core.consumption.domain.Consumption;
import com.wasted.backend.core.consumption.repository.ConsumptionRepository;
import com.wasted.backend.core.consumption.service.converter.ConsumptionConverter;
import com.wasted.backend.core.consumption.validator.ConsumptionValidator;
import com.wasted.backend.core.user.api.exceptions.UserNotFoundException;
import com.wasted.backend.core.user.domain.User;
import com.wasted.backend.core.user.repository.UserRepository;
import com.wasted.backend.shared.entities.ValidationResult;
import org.apache.commons.lang3.time.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class ConsumptionServiceImpl implements ConsumptionService {
    private final ConsumptionRepository consumptionRepository;
    private final ConsumptionConverter consumptionConverter;
    private final ConsumptionValidator consumptionValidator;
    private final UserRepository userRepository;

    @Autowired
    public ConsumptionServiceImpl(final ConsumptionRepository consumptionRepository,
                                  final ConsumptionConverter consumptionConverter,
                                  final ConsumptionValidator consumptionValidator,
                                  final UserRepository userRepository) {
        this.consumptionRepository = consumptionRepository;
        this.consumptionConverter = consumptionConverter;
        this.consumptionValidator = consumptionValidator;
        this.userRepository = userRepository;
    }

    @Override
    public void addConsumption(ConsumptionDto consumptionDto) {
        ValidationResult validationResult = new ValidationResult();
        consumptionValidator.validate(consumptionDto, validationResult);
        validationResult.rejectIfHasErrors();
        Consumption consumption = consumptionConverter.convert(consumptionDto);
        consumptionRepository.save(consumption);
    }

    @Override
    public StatsDto getStatsForUser(String userID) throws UserNotFoundException {
        User user=userRepository.findOneById(userID);
        if (user == null) {
            throw new UserNotFoundException("User not found!");
        }
        Date dateYesterday = DateUtils.addDays(new Date(),-1);
        List<Consumption> list = this.consumptionRepository.findByUserIdAndDateAfter(userID,dateYesterday);
        return null;
    }
}
