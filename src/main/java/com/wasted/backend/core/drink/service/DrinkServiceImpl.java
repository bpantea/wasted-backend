package com.wasted.backend.core.drink.service;

import com.wasted.backend.core.drink.domain.Drink;
import com.wasted.backend.core.drink.exception.DrinkNotFoundException;
import com.wasted.backend.core.drink.repository.DrinkRepository;
import com.wasted.backend.core.drink.validator.DrinkValidator;
import com.wasted.backend.shared.entities.ValidationResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DrinkServiceImpl implements DrinkService {
    Logger logger = LoggerFactory.getLogger(DrinkServiceImpl.class);
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
    public Drink get(String drinkId) {
        logger.info("in get drink");
        Drink drink = drinkRepository.findOneById(drinkId);
        logger.info("Drink {}", drink);
        if (drink == null) {
            throw new RuntimeException();
        }
        return drink;
    }

    @Override
    public List<Drink> getAll() {
        return drinkRepository.findAll();
    }
}
