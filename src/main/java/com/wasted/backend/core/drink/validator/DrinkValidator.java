package com.wasted.backend.core.drink.validator;

import com.wasted.backend.core.drink.domain.Drink;
import com.wasted.backend.shared.entities.ValidationResult;
import org.apache.logging.log4j.util.Strings;
import org.springframework.stereotype.Component;

@Component
public class DrinkValidator {

    public void validate(Drink drink, ValidationResult validationResult) {
        if (drink.getId() == null || drink.getId().equals(Strings.EMPTY)) {
            validationResult.rejectValue("id", "Id cannot be null");
        }

        if (drink.getAlcoholPercentage() == null || drink.getAlcoholPercentage() < 0 || drink.getAlcoholPercentage() > 100) {
            validationResult.rejectValue("percentage", "Alcohol percentage must be between 0 and 100");
        }

        if (drink.getBrand() == null || drink.getBrand().equals(Strings.EMPTY)) {
            validationResult.rejectValue("brand", "Brand must not be null");
        }

        if (drink.getQuantity() == null || drink.getQuantity() <= 0) {
            validationResult.rejectValue("quantity", "Quantity must be greater than 0");
        }
    }
}
