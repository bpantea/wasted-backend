package com.wasted.backend.core.user.api.dtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GoogleUserDto {
    private String id;
    private String displayName;
    private String email;
    private String profilePicture;
}
