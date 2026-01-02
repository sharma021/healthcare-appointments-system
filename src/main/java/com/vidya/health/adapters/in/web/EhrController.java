package com.vidya.health.adapters.in.web;

import com.vidya.health.application.ports.DoctorPort;
import com.vidya.health.application.ports.PatientPort;
import com.vidya.health.application.usecases.EhrUseCase;
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
@RequestMapping("/api/ehr")
public class EhrController {

    private final EhrUseCase ehrUseCase;
    private final PatientPort patientPort;
    private final DoctorPort doctorPort;

    public EhrController(EhrUseCase ehrUseCase, PatientPort patientPort, DoctorPort doctorPort) {
        this.ehrUseCase = ehrUseCase;
        this.patientPort = patientPort;
        this.doctorPort = doctorPort;
    }

    @PostMapping
    @PreAuthorize("hasAnyRole('ADMIN','DOCTOR')")
    public Object add(@Valid @RequestBody AddEhrRequest req) {
        var claims = CurrentUser.claimsOrNull();
        UUID doctorId = req.doctorId();
        if (claims != null && claims.role() == Role.DOCTOR) {
            doctorId = doctorPort.findByUserId(claims.userId()).orElseThrow().id();
        }
        return ehrUseCase.add(req.appointmentId(), req.patientId(), doctorId, req.notes(), req.diagnosis(), CurrentUser.emailOrAnonymous());
    }

    @GetMapping("/patient/{patientId}")
    @PreAuthorize("hasAnyRole('ADMIN','DOCTOR','PATIENT')")
    public List<?> listForPatient(@PathVariable UUID patientId) {
        var claims = CurrentUser.claimsOrNull();
        if (claims != null && claims.role() == Role.PATIENT) {
            patientId = patientPort.findByUserId(claims.userId()).orElseThrow().id();
        }
        return ehrUseCase.listForPatient(patientId);
    }

    public record AddEhrRequest(@NotNull UUID appointmentId, @NotNull UUID patientId, @NotNull UUID doctorId,
                                @NotBlank String notes, String diagnosis) {}
}
