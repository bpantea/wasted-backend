package com.wasted.backend.core.consumption.domain;

import com.wasted.backend.core.drink.domain.Drink;
import com.wasted.backend.core.user.domain.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import java.util.Date;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Consumption {
    @Id
    private Long id;
    @ManyToOne
    private User user;
    @ManyToOne
    private Drink drink;
    @CreationTimestamp
    private Date date;
    private Double quantity;
    private Double alcoholQuantity;
}
