package com.wasted.backend.core.drink.service;

import com.wasted.backend.core.drink.domain.Drink;
import com.wasted.backend.core.drink.exception.DrinkAlreadyPresentException;
import com.wasted.backend.core.drink.exception.DrinkNotFoundException;

import java.util.List;

public interface DrinkService {
    Drink add(Drink drink) throws DrinkAlreadyPresentException;

    void remove(String drinkId) throws DrinkNotFoundException;

    Drink update(Drink drink) throws DrinkNotFoundException;

    Drink get(String drinkId) throws DrinkNotFoundException;

    List<Drink> getAll();
}
