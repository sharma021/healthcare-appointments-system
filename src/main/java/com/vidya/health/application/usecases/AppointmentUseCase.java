package com.vidya.health.application.usecases;

import com.vidya.health.application.ports.*;
import com.vidya.health.application.usecases.dto.AppointmentView;
import com.vidya.health.domain.common.DomainException;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;


import java.time.OffsetDateTime;
import java.util.List;
import java.util.UUID;

@Service
public class AppointmentUseCase {

    private final AppointmentPort appointmentPort;
    private final PatientPort patientPort;
    private final DoctorPort doctorPort;
    private final AuditPort auditPort;

    public AppointmentUseCase(AppointmentPort appointmentPort, PatientPort patientPort, DoctorPort doctorPort, AuditPort auditPort) {
        this.appointmentPort = appointmentPort;
        this.patientPort = patientPort;
        this.doctorPort = doctorPort;
        this.auditPort = auditPort;
    }
    public Page<?> listForPatient(UUID patientId, Pageable pageable) {
        return appointmentPort.listForPatient(patientId, pageable);
    }

    public Page<?> listForDoctor(UUID doctorId, Pageable pageable) {
        return appointmentPort.listForDoctor(doctorId, pageable);
    }

    public AppointmentView schedule(UUID patientId, UUID doctorId, OffsetDateTime startTime, int durationMinutes, String reason, String actorEmail) {
        patientPort.get(patientId).orElseThrow(() -> new DomainException("Patient not found"));
        doctorPort.get(doctorId).orElseThrow(() -> new DomainException("Doctor not found"));

        OffsetDateTime end = startTime.plusMinutes(durationMinutes);
        if (appointmentPort.doctorHasOverlap(doctorId, startTime, end)) {
            throw new DomainException("Doctor is not available for this time slot");
        }

        AppointmentView appt = appointmentPort.create(patientId, doctorId, startTime, durationMinutes, reason);
        auditPort.log("CREATE", "APPOINTMENT", appt.id(), actorEmail, "Scheduled appointment");
        return appt;
    }

    public AppointmentView get(UUID id) {
        return appointmentPort.get(id).orElseThrow(() -> new DomainException("Appointment not found"));
    }

    public List<AppointmentView> listForPatient(UUID patientId) { return appointmentPort.listForPatient(patientId); }

    public List<AppointmentView> listForDoctor(UUID doctorId) { return appointmentPort.listForDoctor(doctorId); }

    public AppointmentView updateStatus(UUID appointmentId, String status, String actorEmail) {
        AppointmentView updated = appointmentPort.updateStatus(appointmentId, status);
        auditPort.log("UPDATE", "APPOINTMENT", updated.id(), actorEmail, "Changed status to " + status);
        return updated;
    }
}
