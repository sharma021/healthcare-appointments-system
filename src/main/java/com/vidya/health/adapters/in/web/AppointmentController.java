package com.vidya.health.adapters.in.web;

import com.vidya.health.application.ports.DoctorPort;
import com.vidya.health.application.ports.PatientPort;
import com.vidya.health.application.usecases.AppointmentUseCase;
import com.vidya.health.config.security.CurrentUser;
import com.vidya.health.domain.user.Role;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import java.time.OffsetDateTime;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/appointments")
public class AppointmentController {

    private final AppointmentUseCase appointmentUseCase;
    private final PatientPort patientPort;
    private final DoctorPort doctorPort;

    public AppointmentController(AppointmentUseCase appointmentUseCase, PatientPort patientPort, DoctorPort doctorPort) {
        this.appointmentUseCase = appointmentUseCase;
        this.patientPort = patientPort;
        this.doctorPort = doctorPort;
    }

    @PostMapping
    @PreAuthorize("hasAnyRole('ADMIN','PATIENT')")
    public Object schedule(@Valid @RequestBody ScheduleRequest req) {
        var claims = CurrentUser.claimsOrNull();
        UUID patientId = req.patientId();
        if (claims != null && claims.role() == Role.PATIENT) {
            patientId = patientPort.findByUserId(claims.userId()).orElseThrow().id();
        }
        return appointmentUseCase.schedule(patientId, req.doctorId(), req.startTime(), req.durationMinutes(), req.reason(), CurrentUser.emailOrAnonymous());
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN','DOCTOR','PATIENT')")
    public Object get(@PathVariable UUID id) { return appointmentUseCase.get(id); }

    @GetMapping("/patient/{patientId}")
    @PreAuthorize("hasAnyRole('ADMIN','PATIENT')")
    public Page<?> listForPatient(@PathVariable UUID patientId,
                                  @PageableDefault(size = 10) Pageable pageable) {
        var claims = CurrentUser.claimsOrNull();
        if (claims != null && claims.role() == Role.PATIENT) {
            patientId = patientPort.findByUserId(claims.userId()).orElseThrow().id();
        }
        return appointmentUseCase.listForPatient(patientId, pageable);
    }

    @GetMapping("/doctor/{doctorId}")
    @PreAuthorize("hasAnyRole('ADMIN','DOCTOR')")
    public Page<?> listForDoctor(@PathVariable UUID doctorId,
                                 @PageableDefault(size = 10) Pageable pageable) {
        var claims = CurrentUser.claimsOrNull();
        if (claims != null && claims.role() == Role.DOCTOR) {
            doctorId = doctorPort.findByUserId(claims.userId()).orElseThrow().id();
        }
        return appointmentUseCase.listForDoctor(doctorId, pageable);
    }


    @PatchMapping("/{id}/status")
    @PreAuthorize("hasAnyRole('ADMIN','DOCTOR')")
    public Object updateStatus(@PathVariable UUID id, @Valid @RequestBody StatusRequest req) {
        return appointmentUseCase.updateStatus(id, req.status(), CurrentUser.emailOrAnonymous());
    }

    public record ScheduleRequest(@NotNull UUID patientId, @NotNull UUID doctorId, @NotNull OffsetDateTime startTime,
                                  @Min(10) int durationMinutes, @NotBlank String reason) {}
    public record StatusRequest(@NotBlank String status) {}
}
