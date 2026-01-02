package com.vidya.health.adapters.in.web;

import com.vidya.health.adapters.out.persistence.UserPersistenceAdapter;
import com.vidya.health.application.usecases.AuthUseCase;
import com.vidya.health.config.security.JwtService;
import com.vidya.health.domain.common.DomainException;
import com.vidya.health.domain.user.Role;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthUseCase authUseCase;
    private final UserPersistenceAdapter userAdapter;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;

    public AuthController(AuthUseCase authUseCase, UserPersistenceAdapter userAdapter, PasswordEncoder passwordEncoder, JwtService jwtService) {
        this.authUseCase = authUseCase;
        this.userAdapter = userAdapter;
        this.passwordEncoder = passwordEncoder;
        this.jwtService = jwtService;
    }

    @PostMapping("/register")
    public ResponseEntity<?> register(@Valid @RequestBody RegisterRequest req) {
        var user = authUseCase.register(req.email(), req.password(), req.role());
        return ResponseEntity.ok(new RegisterResponse(user.id(), user.email(), user.role().name()));
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@Valid @RequestBody LoginRequest req) {
        var user = userAdapter.findEntityByEmail(req.email())
                .orElseThrow(() -> new DomainException("Invalid credentials"));

        if (!passwordEncoder.matches(req.password(), user.getPasswordHash())) {
            throw new DomainException("Invalid credentials");
        }

        String token = jwtService.createAccessToken(user.getId(), user.getEmail(), user.getRole());
        return ResponseEntity.ok(new TokenResponse(token));
    }

    public record RegisterRequest(@NotBlank @Email String email, @NotBlank String password, @NotNull Role role) {}
    public record RegisterResponse(java.util.UUID id, String email, String role) {}
    public record LoginRequest(@NotBlank @Email String email, @NotBlank String password) {}
    public record TokenResponse(String accessToken) {}
}
