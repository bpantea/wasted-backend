package com.wasted.backend.core.drink.service;

import com.wasted.backend.core.drink.domain.Drink;
import com.wasted.backend.core.drink.exception.DrinkNotFoundException;
import com.wasted.backend.core.drink.repository.DrinkRepository;
import com.wasted.backend.core.drink.validator.DrinkValidator;
import com.wasted.backend.shared.ValidationResult;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class DrinkServiceImpl implements DrinkService {
    private final DrinkRepository drinkRepository;
    private final DrinkValidator drinkValidator;

    @Autowired
    public DrinkServiceImpl(final DrinkRepository drinkRepository,
                            final DrinkValidator drinkValidator) {
        this.drinkRepository = drinkRepository;
        this.drinkValidator = drinkValidator;
    }

    @Override
    public Drink add(Drink drink) {
        ValidationResult validationResult = new ValidationResult();
        drinkValidator.validate(drink, validationResult);
        validationResult.rejectIfHasErrors();
        return drinkRepository.save(drink);
    }

    @Override
    public void remove(String drinkId) throws DrinkNotFoundException {
        Drink drink = drinkRepository.findOneById(drinkId);
        if (drink == null) {
            throw new DrinkNotFoundException("Drink not found");
        }
        drinkRepository.delete(drink);
    }

    @Override
    public Drink update(Drink drink) throws DrinkNotFoundException {
        ValidationResult validationResult = new ValidationResult();
        drinkValidator.validate(drink, validationResult);
        validationResult.rejectIfHasErrors();

        Drink oldDrink = drinkRepository.findOneById(drink.getId());
        if (oldDrink == null) {
            throw new DrinkNotFoundException("Drink not found");
        }
        return drinkRepository.save(drink);
    }

    @Override
    public Drink get(String drinkId) throws DrinkNotFoundException {
        Drink drink = drinkRepository.findOneById(drinkId);
        if (drink == null) {
            throw new DrinkNotFoundException("Drink not found");
        }
        return drink;
    }

    @Override
    public List<Drink> getAll() {
        return null;
    }
}
