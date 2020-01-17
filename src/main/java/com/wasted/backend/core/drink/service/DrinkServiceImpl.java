package com.wasted.backend.core.drink.service;

import com.wasted.backend.core.drink.api.DrinkDto;
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
    public DrinkDto add(DrinkDto drink) {
        Drink realDrink = Drink.builder()
                .id(drink.getId())
                .alcoholQuantity(drink.getAlcoholQuantity())
                .kcal(drink.getKcal())
                .model(drink.getModel())
                .brand(drink.getBrand())
                .quantity(drink.getQuantity())
                .build();

        Double percent = drink.getAlcoholQuantity();
        Double alcoholQuantity = (percent / 100.0) * drink.getQuantity();
        logger.info("percent {}, alcoholQuantity {}", percent, alcoholQuantity);
        realDrink.setAlcoholQuantity(alcoholQuantity);
        ValidationResult validationResult = new ValidationResult();
        drinkValidator.validate(realDrink, validationResult);
        validationResult.rejectIfHasErrors();
        drinkRepository.save(realDrink);
        return drink;
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
    public DrinkDto get(String drinkId) {
        logger.info("in get drink");
        Drink drink = drinkRepository.findOneById(drinkId);
        logger.info("Drink {}", drink);
        if (drink == null) {
            throw new RuntimeException();
        }

        DrinkDto drinkDto = DrinkDto.builder()
                .id(drink.getId())
                .alcoholQuantity(drink.getAlcoholQuantity())
                .kcal(drink.getKcal())
                .model(drink.getModel())
                .brand(drink.getBrand())
                .quantity(drink.getQuantity())
                .build();
        drinkDto.setAlcoholQuantity(drink.getAlcoholQuantity() / drink.getQuantity() * 100.0);
        logger.info("database alcohol {}, dto alcohol {}", drink.getAlcoholQuantity(), drinkDto.getAlcoholQuantity());
        return drinkDto;
    }

    @Override
    public List<Drink> getAll() {
        return drinkRepository.findAll();
    }
}
