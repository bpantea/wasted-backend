package com.wasted.backend.core.consumption.repository;

import com.wasted.backend.core.consumption.domain.Consumption;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ConsumptionRepository extends JpaRepository<Consumption, Long> {
}
