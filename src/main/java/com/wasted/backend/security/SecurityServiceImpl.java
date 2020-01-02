package com.wasted.backend.security;

import com.wasted.backend.core.user.domain.User;
import com.wasted.backend.core.user.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.security.Principal;

@Service
public class SecurityServiceImpl implements SecurityService {
    private final UserRepository userRepository;

    @Autowired
    public SecurityServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public User getCurrentUser() {
        return userRepository.findOneById(getIdFromAuth());
    }

    private String getIdFromAuth() {
        final SecurityContext securityContext = SecurityContextHolder.getContext();
        final Authentication auth = securityContext.getAuthentication();
        //getAuthentication may return null if no auth info is available
        if (auth != null) {
            final Object principal = auth.getPrincipal();
            //getPrincipal returns a string object for anonymous users
            if (principal instanceof Principal) {
                return ((Principal) principal).getName();
            }
        }
        return null;
    }
}
