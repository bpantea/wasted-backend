package com.wasted.backend.core.user.service;

import com.wasted.backend.core.user.api.dtos.ExtraFieldsUser;
import com.wasted.backend.core.user.api.dtos.GoogleUserDto;
import com.wasted.backend.core.user.domain.User;
import com.wasted.backend.core.user.repository.UserRepository;
import com.wasted.backend.core.user.service.converter.UserConverter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {
    Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);

    private final UserRepository userRepository;
    private final UserConverter userConverter;

    @Autowired
    public UserServiceImpl(final UserRepository userRepository,
                           final UserConverter userConverter) {
        this.userRepository = userRepository;
        this.userConverter = userConverter;
    }

    @Override
    public synchronized User saveUser(GoogleUserDto user) {
        logger.info("saveUser");
        User currentUser = userRepository.findOneById(user.getId());
        if (currentUser == null) {
            currentUser = new User();
            logger.info("user is null");
        }
        logger.info("before convert");
        logger.info("id user {}", user.getId());
        currentUser = userConverter.convert(user, currentUser);
        logger.info("user converted");
        try {
            User savedUser = userRepository.save(currentUser);
            logger.info("after save");
            return savedUser;
        } catch (Exception ex) {
            logger.error(ex.getMessage());
            return null;
        }
    }

    @Override
    public User saveUser(User user) {
        return userRepository.save(user);
    }

    @Override
    public User getUser(String id) {
        return userRepository.findOneById(id);
    }

    @Override
    public User putExtraFields(String userId, ExtraFieldsUser user) {
        logger.info("extra fields");
        User currentUser = userRepository.findOneById(userId);
        currentUser.setBirthday(user.getBirthday());
        currentUser.setGender(user.getGender());
        currentUser.setWeight(user.getWeight());
        logger.info("extra fields {} {} {} {}", userId, user.getBirthday(), user.getGender(), user.getWeight());
        User savedUser = userRepository.save(currentUser);
        logger.info("user {}", savedUser);
        return savedUser;
    }
}
