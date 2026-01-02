package com.vidya.health.application.ports;

import com.vidya.health.application.usecases.dto.UserView;
import com.vidya.health.domain.user.Role;

import java.util.Optional;
import java.util.UUID;

public interface UserPort {
    Optional<UserView> findByEmail(String email);
    Optional<UserView> findById(UUID id);
    UserView createUser(String email, String passwordHash, Role role);
    void setPassword(UUID userId, String passwordHash);
}
