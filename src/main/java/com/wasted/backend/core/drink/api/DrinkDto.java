package com.wasted.backend.core.drink.api;

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
public class DrinkDto {
    private String id;
    private Double quantity;
    private String brand;
    private String model;
    private Double alcoholQuantity;
    private Double kcal;
}
