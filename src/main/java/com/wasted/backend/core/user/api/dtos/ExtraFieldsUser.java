package com.wasted.backend.core.user.api.dtos;

import com.wasted.backend.core.user.domain.Gender;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ExtraFieldsUser {
    private Date birthday;
    private Double weight;
    private Gender gender;
}
