package com.wasted.backend.core.user.repository;

import com.wasted.backend.core.user.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    User findOneById(String id);

    User findByEmail(String email);
}
