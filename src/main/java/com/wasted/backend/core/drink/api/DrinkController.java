package com.wasted.backend.core.drink.api;

import com.wasted.backend.core.drink.domain.Drink;
import com.wasted.backend.core.drink.exception.DrinkAlreadyPresentException;
import com.wasted.backend.core.drink.exception.DrinkNotFoundException;
import com.wasted.backend.core.drink.service.DrinkService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/drink")
public class DrinkController {
    private final DrinkService drinkService;

    @Autowired
    public DrinkController(final DrinkService drinkService) {
        this.drinkService = drinkService;
    }

    @GetMapping("/getOne/{id}")
    public Drink getDrink(@PathVariable("id") String drinkId) throws DrinkNotFoundException {
        return drinkService.get(drinkId);
    }

    @GetMapping("/all")
    public List<Drink> getDrinks() {
        return drinkService.getAll();
    }

    @PostMapping
    public Drink addDrink(@RequestBody Drink drink) {
        try {
            return drinkService.add(drink);
        } catch (DrinkAlreadyPresentException e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    @PutMapping
    public Drink updateDrink(@RequestBody Drink drink) throws DrinkNotFoundException {
        return drinkService.update(drink);
    }

    @DeleteMapping("/{id}")
    public void removeDrink(@PathVariable("id") String drinkId) throws DrinkNotFoundException {
        drinkService.remove(drinkId);
    }
}
