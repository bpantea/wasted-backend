package com.wasted.backend.core.consumption.validator;

import com.wasted.backend.core.consumption.api.dtos.ConsumptionDto;
import com.wasted.backend.core.drink.repository.DrinkRepository;
import com.wasted.backend.core.user.repository.UserRepository;
import com.wasted.backend.shared.entities.ValidationResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ConsumptionValidator {
    private final UserRepository userRepository;
    private final DrinkRepository drinkRepository;

    @Autowired
    public ConsumptionValidator(final UserRepository userRepository,
                                final DrinkRepository drinkRepository) {
        this.userRepository = userRepository;
        this.drinkRepository = drinkRepository;
    }

    public void validate(ConsumptionDto consumptionDto, ValidationResult errors) {
        if (userRepository.findOneById(consumptionDto.getUserId()) == null) {
            errors.rejectValue("userId", "User not found");
        }
        if (drinkRepository.findOneById(consumptionDto.getDrinkId()) == null) {
            errors.rejectValue("drinkId", "Drink not found");
        }
        if (consumptionDto.getQuantity() == null || consumptionDto.getQuantity() <= 0) {
            errors.rejectValue("quantity", "Quantity must be greater that 0");
        }
    }
}
