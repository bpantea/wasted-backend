package com.wasted.backend.core.drink.service;

import com.wasted.backend.core.drink.domain.Drink;
import com.wasted.backend.core.drink.exception.DrinkAlreadyPresentException;
import com.wasted.backend.core.drink.exception.DrinkNotFoundException;

import java.util.List;

public interface DrinkService {
    Drink add(Drink drink);

    void remove(String drinkId) throws DrinkNotFoundException;

    Drink get(String drinkId);

    List<Drink> getAll();
}
