package com.wasted.backend.core.consumption.api.dtos;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class StatsDto {
    private Double percentAlcohol;
    private Double absortionTime;
    private Double kcalsNumber;
}
