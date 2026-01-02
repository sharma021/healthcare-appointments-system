package com.vidya.health.adapters.in.web;

import com.vidya.health.application.ports.DoctorPort;
import com.vidya.health.application.usecases.DoctorUseCase;
import com.vidya.health.config.security.CurrentUser;
import com.vidya.health.domain.user.Role;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/doctors")
public class DoctorController {

    private final DoctorUseCase doctorUseCase;
    private final DoctorPort doctorPort;

    public DoctorController(DoctorUseCase doctorUseCase, DoctorPort doctorPort) {
        this.doctorUseCase = doctorUseCase;
        this.doctorPort = doctorPort;
    }

    @GetMapping
    public List<?> list() { return doctorUseCase.list(); }

    @GetMapping("/{id}")
    public Object get(@PathVariable UUID id) { return doctorUseCase.get(id); }

    @PostMapping
    @PreAuthorize("hasAnyRole('ADMIN','DOCTOR')")
    public Object create(@Valid @RequestBody DoctorCreateRequest req) {
        var claims = CurrentUser.claimsOrNull();
        UUID userId = (claims != null && claims.role() == Role.DOCTOR) ? claims.userId() : req.userId();
        return doctorUseCase.create(userId, req.fullName(), req.specialization(), CurrentUser.emailOrAnonymous());
    }

    @GetMapping("/me")
    @PreAuthorize("hasRole('DOCTOR')")
    public Object me() {
        var claims = CurrentUser.claimsOrNull();
        return doctorPort.findByUserId(claims.userId()).orElse(null);
    }

    public record DoctorCreateRequest(@NotNull UUID userId, @NotBlank String fullName, @NotBlank String specialization) {}
}
