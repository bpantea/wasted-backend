package com.wasted.backend.core.consumption.service.converter;

import com.wasted.backend.core.consumption.api.dtos.ConsumptionDto;
import com.wasted.backend.core.consumption.domain.Consumption;
import com.wasted.backend.core.drink.repository.DrinkRepository;
import com.wasted.backend.core.user.repository.UserRepository;
import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.ReportingPolicy;
import org.springframework.beans.factory.annotation.Autowired;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE)
public abstract class ConsumptionConverter {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private DrinkRepository drinkRepository;

    public abstract Consumption convert(ConsumptionDto consumptionDto);

    @AfterMapping
    protected void after(ConsumptionDto consumptionDto, @MappingTarget Consumption consumption) {
        consumption.setUser(userRepository.findOneById(consumptionDto.getUserId()));
        consumption.setDrink(drinkRepository.findOneById(consumptionDto.getDrinkId()));

        Double factor = consumptionDto.getQuantity() / consumption.getDrink().getQuantity();
        consumption.setAlcoholQuantity(factor * consumption.getDrink().getAlcoholQuantity());
    }
}
