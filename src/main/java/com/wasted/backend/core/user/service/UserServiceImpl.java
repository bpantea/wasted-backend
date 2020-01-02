package com.wasted.backend.core.user.service;

import com.wasted.backend.core.user.api.dtos.GoogleUserDto;
import com.wasted.backend.core.user.domain.User;
import com.wasted.backend.core.user.repository.UserRepository;
import com.wasted.backend.core.user.service.converter.UserConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final UserConverter userConverter;

    @Autowired
    public UserServiceImpl(final UserRepository userRepository,
                           final UserConverter userConverter) {
        this.userRepository = userRepository;
        this.userConverter = userConverter;
    }

    @Override
    public synchronized void saveUser(GoogleUserDto user) {
        User currentUser = userRepository.findOneById(user.getId());
        if (currentUser == null) {
            currentUser = new User();
        }

        currentUser = userConverter.convert(user, currentUser);
        userRepository.save(currentUser);
    }
}
