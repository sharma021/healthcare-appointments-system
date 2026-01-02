package com.vidya.health.application.ports;

import com.vidya.health.application.usecases.dto.AppointmentView;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface AppointmentPort {
    Page<?> listForPatient(UUID patientId, Pageable pageable);
    Page<?> listForDoctor(UUID doctorId, Pageable pageable);
    AppointmentView create(UUID patientId, UUID doctorId, OffsetDateTime startTime, int durationMinutes, String reason);
    Optional<AppointmentView> get(UUID appointmentId);
    List<AppointmentView> listForPatient(UUID patientId);
    List<AppointmentView> listForDoctor(UUID doctorId);
    AppointmentView updateStatus(UUID appointmentId, String status);
    boolean doctorHasOverlap(UUID doctorId, OffsetDateTime startTime, OffsetDateTime endTime);
}
