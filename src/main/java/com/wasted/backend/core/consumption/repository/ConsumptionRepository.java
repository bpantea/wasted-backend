package com.wasted.backend.core.consumption.repository;

import com.wasted.backend.core.consumption.domain.Consumption;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Date;
import java.util.List;

public interface ConsumptionRepository extends JpaRepository<Consumption, Long> {
    List<Consumption> findByUserIdAndDateAfter(String userid, Date date);
}
