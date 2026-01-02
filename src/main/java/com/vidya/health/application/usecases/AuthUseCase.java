package com.vidya.health.application.usecases;

import com.vidya.health.application.ports.UserPort;
import com.vidya.health.application.usecases.dto.UserView;
import com.vidya.health.domain.common.DomainException;
import com.vidya.health.domain.user.Role;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthUseCase {

    private final UserPort userPort;
    private final PasswordEncoder passwordEncoder;

    public AuthUseCase(UserPort userPort, PasswordEncoder passwordEncoder) {
        this.userPort = userPort;
        this.passwordEncoder = passwordEncoder;
    }

    public UserView register(String email, String rawPassword, Role role) {
        if (userPort.findByEmail(email).isPresent()) {
            throw new DomainException("Email already registered");
        }
        String hash = passwordEncoder.encode(rawPassword);
        return userPort.createUser(email.toLowerCase(), hash, role);
    }
}
