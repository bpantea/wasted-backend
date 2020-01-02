package com.wasted.backend.core.consumption.api.dtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ConsumptionDto {
    private Long id;
    private String userId;
    private String drinkId;
    private Double quantity;
}
