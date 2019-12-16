package com.wasted.backend.core.user.repository;

import com.wasted.backend.core.user.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
}
