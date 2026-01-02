package com.vidya.health.adapters.out.persistence.jpa;

import jakarta.persistence.*;
import java.time.OffsetDateTime;
import java.util.UUID;

@Entity
@Table(name="appointments",
        indexes = {
                @Index(name="idx_appt_doctor_time", columnList = "doctor_id,start_time"),
                @Index(name="idx_appt_patient", columnList = "patient_id")
        })
public class AppointmentJpaEntity {

    @Id
    @Column(columnDefinition = "uuid")
    private UUID id;

    @Column(name="patient_id", nullable=false, columnDefinition = "uuid")
    private UUID patientId;

    @Column(name="doctor_id", nullable=false, columnDefinition = "uuid")
    private UUID doctorId;

    @Column(name="start_time", nullable=false)
    private OffsetDateTime startTime;

    @Column(name="duration_minutes", nullable=false)
    private int durationMinutes;

    @Column(length = 250)
    private String reason;

    @Enumerated(EnumType.STRING)
    @Column(nullable=false, length = 20)
    private AppointmentStatus status;

    protected AppointmentJpaEntity() {}

    public AppointmentJpaEntity(UUID id, UUID patientId, UUID doctorId, OffsetDateTime startTime, int durationMinutes, String reason, AppointmentStatus status) {
        this.id = id;
        this.patientId = patientId;
        this.doctorId = doctorId;
        this.startTime = startTime;
        this.durationMinutes = durationMinutes;
        this.reason = reason;
        this.status = status;
    }

    public UUID getId() { return id; }
    public UUID getPatientId() { return patientId; }
    public UUID getDoctorId() { return doctorId; }
    public OffsetDateTime getStartTime() { return startTime; }
    public int getDurationMinutes() { return durationMinutes; }
    public String getReason() { return reason; }
    public AppointmentStatus getStatus() { return status; }
    public void setStatus(AppointmentStatus status) { this.status = status; }
}
