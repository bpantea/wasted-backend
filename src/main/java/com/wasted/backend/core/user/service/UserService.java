package com.wasted.backend.core.user.service;

import com.wasted.backend.core.user.api.dtos.GoogleUserDto;
import com.wasted.backend.core.user.domain.User;

public interface UserService {
    User saveUser(GoogleUserDto user);

    User saveUser(User user);

    User getUser(String id);
}
