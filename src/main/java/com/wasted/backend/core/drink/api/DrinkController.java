package com.wasted.backend.core.drink.api;

import com.wasted.backend.core.drink.domain.Drink;
import com.wasted.backend.core.drink.exception.DrinkNotFoundException;
import com.wasted.backend.core.drink.service.DrinkService;
import com.wasted.backend.shared.exceptions.RestErrorException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/drink")
public class DrinkController {
    private final DrinkService drinkService;
    Logger logger = LoggerFactory.getLogger(DrinkController.class);


    @Autowired
    public DrinkController(final DrinkService drinkService) {
        this.drinkService = drinkService;
    }

    @GetMapping("/getOne/{id}")
    public DrinkDto getDrink(@PathVariable("id") String drinkId) {
        logger.info("in get drink");
        return drinkService.get(drinkId);
    }

    @PostMapping
    public DrinkDto addDrink(@RequestBody DrinkDto drink) {
        return drinkService.add(drink);
    }

    @DeleteMapping("/{id}")
    public void removeDrink(@PathVariable("id") String drinkId) {
        try {
            drinkService.remove(drinkId);
        } catch (DrinkNotFoundException e) {
            throw new RestErrorException(e.getMessage());
        }
    }
}
