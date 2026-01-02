package com.vidya.health.adapters.in.web;

import com.vidya.health.application.ports.PatientPort;
import com.vidya.health.application.usecases.PatientUseCase;
import com.vidya.health.config.security.CurrentUser;
import com.vidya.health.domain.user.Role;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/patients")
public class PatientController {

    private final PatientUseCase patientUseCase;
    private final PatientPort patientPort;

    public PatientController(PatientUseCase patientUseCase, PatientPort patientPort) {
        this.patientUseCase = patientUseCase;
        this.patientPort = patientPort;
    }

    @PostMapping
    @PreAuthorize("hasAnyRole('ADMIN','PATIENT')")
    public Object create(@Valid @RequestBody PatientCreateRequest req) {
        var claims = CurrentUser.claimsOrNull();
        UUID userId = (claims != null && claims.role() == Role.PATIENT) ? claims.userId() : req.userId();
        return patientUseCase.create(userId, req.fullName(), req.phone(), req.dobIso(), CurrentUser.emailOrAnonymous());
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN','DOCTOR','PATIENT')")
    public Object get(@PathVariable UUID id) { return patientUseCase.get(id); }

    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    public Page<?> list(@PageableDefault(size = 10) Pageable pageable) {
        return patientUseCase.list(pageable);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN','PATIENT')")
    public Object update(@PathVariable UUID id, @Valid @RequestBody PatientUpdateRequest req) {
        return patientUseCase.update(id, req.fullName(), req.phone(), req.dobIso(), CurrentUser.emailOrAnonymous());
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public void delete(@PathVariable UUID id) { patientUseCase.delete(id, CurrentUser.emailOrAnonymous()); }

    @GetMapping("/me")
    @PreAuthorize("hasRole('PATIENT')")
    public Object me() {
        var claims = CurrentUser.claimsOrNull();
        return patientPort.findByUserId(claims.userId()).orElse(null);
    }

    public record PatientCreateRequest(@NotNull UUID userId, @NotBlank String fullName, String phone, String dobIso) {}
    public record PatientUpdateRequest(@NotBlank String fullName, String phone, String dobIso) {}
}
