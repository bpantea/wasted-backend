package com.wasted.backend.core.consumption.api;

import com.wasted.backend.core.consumption.api.dtos.ConsumptionDto;
import com.wasted.backend.core.consumption.api.dtos.StatsDto;
import com.wasted.backend.core.consumption.service.ConsumptionService;
import com.wasted.backend.core.user.api.exceptions.UserNotFoundException;
import com.wasted.backend.shared.exceptions.RestErrorException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/consumption")
public class ConsumptionController {
    private final ConsumptionService consumptionService;

    @Autowired
    public ConsumptionController(ConsumptionService consumptionService) {
        this.consumptionService = consumptionService;
    }

    @PostMapping
    public ResponseEntity<Object> addConsumption(@RequestBody ConsumptionDto consumptionDto) {
        this.consumptionService.addConsumption(consumptionDto);
        return ResponseEntity.ok(true);
    }

    @GetMapping("/stats/{idUser}")
    public ResponseEntity<StatsDto> getByCurrentUser(@PathVariable ("idUser") String idUser){
        try {
            return ResponseEntity.ok(consumptionService.getStatsForUser(idUser));
        } catch (UserNotFoundException e) {
            throw new RestErrorException(e.getMessage());
        }
    }
}
