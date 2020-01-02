package com.wasted.backend.core.user.service;

import com.wasted.backend.core.user.api.dtos.GoogleUserDto;

public interface UserService {
    void saveUser(GoogleUserDto user);
}
