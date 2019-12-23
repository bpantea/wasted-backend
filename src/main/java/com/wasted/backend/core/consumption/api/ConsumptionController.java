package com.wasted.backend.core.consumption.api;

import com.wasted.backend.core.consumption.api.dtos.ConsumptionDto;
import com.wasted.backend.core.consumption.service.ConsumptionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
}
