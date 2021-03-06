package com.wasted.backend.core.consumption.service;

import com.wasted.backend.core.consumption.api.dtos.ConsumptionDto;
import com.wasted.backend.core.consumption.api.dtos.StatsDto;
import com.wasted.backend.core.consumption.domain.Consumption;
import com.wasted.backend.core.consumption.repository.ConsumptionRepository;
import com.wasted.backend.core.consumption.service.converter.ConsumptionConverter;
import com.wasted.backend.core.consumption.validator.ConsumptionValidator;
import com.wasted.backend.core.user.api.exceptions.UserNotFoundException;
import com.wasted.backend.core.user.domain.Gender;
import com.wasted.backend.core.user.domain.User;
import com.wasted.backend.core.user.repository.UserRepository;
import com.wasted.backend.shared.entities.ValidationResult;
import org.apache.commons.lang3.time.DateUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class ConsumptionServiceImpl implements ConsumptionService {
    Logger logger = LoggerFactory.getLogger(ConsumptionServiceImpl.class);

    private final ConsumptionRepository consumptionRepository;
    private final ConsumptionConverter consumptionConverter;
    private final ConsumptionValidator consumptionValidator;
    private final UserRepository userRepository;

    @Autowired
    public ConsumptionServiceImpl(final ConsumptionRepository consumptionRepository,
                                  final ConsumptionConverter consumptionConverter,
                                  final ConsumptionValidator consumptionValidator,
                                  final UserRepository userRepository) {
        this.consumptionRepository = consumptionRepository;
        this.consumptionConverter = consumptionConverter;
        this.consumptionValidator = consumptionValidator;
        this.userRepository = userRepository;
    }

    @Override
    public void addConsumption(ConsumptionDto consumptionDto) {
        ValidationResult validationResult = new ValidationResult();
        consumptionValidator.validate(consumptionDto, validationResult);
        validationResult.rejectIfHasErrors();
        Consumption consumption = consumptionConverter.convert(consumptionDto);
        consumptionRepository.save(consumption);
    }

    @Override
    public StatsDto getStatsForUser(String userID) throws UserNotFoundException {
        logger.info("get stats");
        User user = userRepository.findOneById(userID);
        if (user == null) {
            logger.info("user not found");
            throw new UserNotFoundException("User not found!");
        }
        Date dateYesterday = DateUtils.addDays(new Date(),-1);
        List<Consumption> consumptionLists = this.consumptionRepository.findByUserIdAndDateAfter(userID,dateYesterday);
        if (consumptionLists.isEmpty()) {
            logger.info("empty list");
            return new StatsDto(0.0,0.0,0.0);
        }

        double userWeight = user.getWeight();
        double r = getRationForGender(user.getGender());
        logger.info("ratio {}", r);

        StatsDto statsDto = new StatsDto();
        statsDto.setKcalsNumber(getSumOfKcal(consumptionLists));
        statsDto.setPercentAlcohol(getBloodAlcoholPercentInBlood(consumptionLists,userWeight,r));
        statsDto.setAbsortionTime(statsDto.getPercentAlcohol() / 0.015);
        logger.info("Number of drinks {}, absorption time {}, kcals {}, percent {}",
                consumptionLists.size(), statsDto.getAbsortionTime(), statsDto.getKcalsNumber(), statsDto.getPercentAlcohol());
        return statsDto;
    }

    private Double getRationForGender(Gender gender){
        double r = 0.0;
        switch (gender){
            case FEMALE:
                r = 0.55;
                break;
            case MALE:
                r = 0.68;
                break;
            case OTHER:
                r = (0.55 + 0.68) / 2.0;
                break;
        }
        return r;
    }

    private Double getSumOfKcal(List<Consumption> consumptionLists){
        double sume = 0.0;
        for (Consumption consumption : consumptionLists){
            sume = sume + consumption.getKcal();
        }
        return sume;
    }


    /*
    * I aproximated BAC as the sum of each BAC for every king of consumtion with alcohol quantity between 0-5 , 5.1 - 12, 12.1 -100
    * docs: https://www.wikihow.com/Calculate-Blood-Alcohol-Content-(Widmark-Formula)
    * */
    private Double getBloodAlcoholPercentInBlood(List<Consumption>consumptionList,Double userWeight,Double r){
        double bac = 0;
        Date datt = DateUtils.addDays(new Date(), -1);
        long dat = new Date().getTime() - 86400000;
        long no = new Date().getTime();
        double aux;
        double au;
        for(Consumption c : consumptionList){
            System.out.println(c.getAlcoholQuantity());
            aux = (c.getAlcoholQuantity() / (userWeight*1000 * r)) * 100;
            au = (c.getDate().getTime() - datt.getTime()) / (60*60 * 1000);
            bac = bac - au*0.015;
            if(bac < 0) bac = 0;
            bac += aux;
            datt = c.getDate();
        }
        au = (new Date().getTime() - datt.getTime()) / (60*60 * 1000);
        bac = bac - au * 0.015;
        if(bac < 0) bac = 0;
        return bac;
    }

    /*
    * by USA model: one standard drink contains 14 grams of pure alcohol which is found in:
    *       - 12 ounces ~= 340.19 grams of a regular beer with 5% alcohol   <equivalent of the size of a beer>
    *       - 5 ounces ~= 141.74 grams of a regular wine with 12% alcohol   <equivalent of a cup of wine>
    *       - 1.5 ounces ~= 42.52 grams of regular wiskey with 40% alcohol  <equivalent of a cup of wiskey>
    * */
    /*
    * alcohol dose for drinks with alcohol quantity between 2 values
    * assume that one consumption is a cup of something (or a fully bootle of beer)
    * */
    private Double alcoholDose(List<Consumption>list, Double a, Double b){
        int nr = 0;
        for(Consumption consumption : list){
            if (consumption.getAlcoholQuantity() >= a && consumption.getAlcoholQuantity() <= b) {
                   nr = nr + 1;       
            }
        }
        return nr * 1.0 * 14;
    }

    /*
    * Alcohol dose / (body weight * r)        r=gender constant
    * */
    private Double BACBeforeElapsedTime(List<Consumption>list,Double a,Double b,Double userWeight,Double r){
        double weightInGrams = userWeight * 1000;
        return alcoholDose(list,a,b) / (weightInGrams * r);
    }

   /*
   * Bac after elapsed time = Bac before elapsed time - time * 0.015
   * time in hours
   * */
    private Double BACAfterElapsedTime(List<Consumption>list,Double a,Double b,Double userWeight, Double r){
        double before = BACBeforeElapsedTime(list,a,b,userWeight,r);
        if (before == 0.0) { //there is no consumptions with the alcohol percentage between a and b
            return 0.0;
        }
        return before - getElapsedTimeFrom(list) * 0.015;
    }

    private Long getElapsedTimeFrom(List<Consumption>list){
        Date maxDate = list.stream().map(Consumption::getDate).max(Date::compareTo).get();
        Date minDate = list.stream().map(Consumption::getDate).min(Date::compareTo).get();
        long nrSec = (maxDate.getTime() - minDate.getTime())/1000;
        return nrSec/3600;
    }
}
