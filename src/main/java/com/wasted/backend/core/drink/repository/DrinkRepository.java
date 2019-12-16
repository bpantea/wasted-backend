package com.wasted.backend.core.drink.repository;

import com.wasted.backend.core.drink.domain.Drink;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DrinkRepository extends JpaRepository<Drink, String> {
    Drink findOneById(String id);
}
