package com.wasted.backend.core.drink.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Drink {
    @Id
    private String id;
    private Double quantity;
    private String brand;
    private String model;
    private Double alcoholQuantity;
    private Double kcal;
}
