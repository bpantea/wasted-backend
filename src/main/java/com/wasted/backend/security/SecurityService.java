package com.wasted.backend.security;

import com.wasted.backend.core.user.domain.User;

public interface SecurityService {
    User getCurrentUser();
}
