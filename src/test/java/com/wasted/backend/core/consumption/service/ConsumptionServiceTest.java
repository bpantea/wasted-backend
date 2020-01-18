package com.wasted.backend.core.consumption.service;

import com.wasted.backend.core.consumption.api.dtos.ConsumptionDto;
import com.wasted.backend.core.consumption.api.dtos.StatsDto;
import com.wasted.backend.core.consumption.domain.Consumption;
import com.wasted.backend.core.consumption.repository.ConsumptionRepository;
import com.wasted.backend.core.drink.domain.Drink;
import com.wasted.backend.core.drink.repository.DrinkRepository;
import com.wasted.backend.core.user.api.exceptions.UserNotFoundException;
import com.wasted.backend.core.user.domain.Gender;
import com.wasted.backend.core.user.domain.User;
import com.wasted.backend.core.user.repository.UserRepository;
import org.apache.commons.lang3.time.DateUtils;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.util.Assert;

import javax.transaction.Transactional;
import java.util.Date;
import java.util.List;

@SpringBootTest
@Transactional
public class ConsumptionServiceTest {
    @Autowired
    private ConsumptionService service;
    @Autowired
    private ConsumptionRepository consumptionRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private DrinkRepository drinkRepository;
    @Autowired
    private ConsumptionService consumptionService;

    @Test
    public void testNumberSec() throws InterruptedException, UserNotFoundException {
        User user = User.builder().id("1").weight(80.0).gender(Gender.MALE).build();
        userRepository.save(user);
        Drink drink = Drink.builder().id("1").build();
        drinkRepository.save(drink);
        Consumption consumption1 = Consumption.builder().id(1L).kcal(100.0).user(user).drink(drink).alcoholQuantity(50.0).date(DateUtils.addHours(new Date(), -20)).build();
        Consumption consumption2 = Consumption.builder().id(2L).kcal(100.0).user(user).drink(drink).alcoholQuantity(50.0).date(DateUtils.addHours(new Date(), -20)).build();
        Consumption consumption3 = Consumption.builder().id(3L).kcal(100.0).user(user).drink(drink).alcoholQuantity(50.0).date(DateUtils.addHours(new Date(), -20)).build();
        Consumption consumption4 = Consumption.builder().id(4L).kcal(100.0).user(user).drink(drink).alcoholQuantity(50.0).date(DateUtils.addHours(new Date(), -1)).build();
        consumption1 = consumptionRepository.save(consumption1);
        consumption2 = consumptionRepository.save(consumption2);
        consumption3 = consumptionRepository.save(consumption3);
        consumption4 = consumptionRepository.save(consumption4);
        Date dateYesterday = DateUtils.addDays(new Date(),-1);
        List<Consumption> all = consumptionRepository.findByUserIdAndDateAfter(user.getId(), dateYesterday);

        StatsDto statsDto = consumptionService.getStatsForUser(user.getId());
    }



}