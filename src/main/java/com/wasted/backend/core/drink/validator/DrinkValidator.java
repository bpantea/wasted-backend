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

        if (drink.getAlcoholQuantity() == null || drink.getAlcoholQuantity() < 0 || drink.getAlcoholQuantity() > drink.getQuantity()) {
            validationResult.rejectValue("alcohol", "Alcohol quantity must be between 0 and drink quantity");
        }

        if (drink.getBrand() == null || drink.getBrand().equals(Strings.EMPTY)) {
            validationResult.rejectValue("brand", "Brand must not be null");
        }

        if (drink.getQuantity() == null || drink.getQuantity() <= 0) {
            validationResult.rejectValue("quantity", "Quantity must be greater than 0");
        }

        if (drink.getKcal() == null || drink.getKcal() < 0) {
            validationResult.rejectValue("kcal", "Kcal must be greater or equal to 0");
        }
    }
}
