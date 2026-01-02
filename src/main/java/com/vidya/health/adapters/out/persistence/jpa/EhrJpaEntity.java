package com.vidya.health.adapters.out.persistence.jpa;

import jakarta.persistence.*;
import java.time.OffsetDateTime;
import java.util.UUID;

@Entity
@Table(name="ehr_records", indexes = {@Index(name="idx_ehr_patient", columnList = "patient_id")})
public class EhrJpaEntity {

    @Id
    @Column(columnDefinition = "uuid")
    private UUID id;

    @Column(name="appointment_id", nullable=false, columnDefinition = "uuid")
    private UUID appointmentId;

    @Column(name="patient_id", nullable=false, columnDefinition = "uuid")
    private UUID patientId;

    @Column(name="doctor_id", nullable=false, columnDefinition = "uuid")
    private UUID doctorId;

    @Column(columnDefinition = "text", nullable=false)
    private String notes;

    @Column(length = 200)
    private String diagnosis;

    @Column(name="created_at", nullable=false)
    private OffsetDateTime createdAt;

    protected EhrJpaEntity() {}

    public EhrJpaEntity(UUID id, UUID appointmentId, UUID patientId, UUID doctorId, String notes, String diagnosis, OffsetDateTime createdAt) {
        this.id = id;
        this.appointmentId = appointmentId;
        this.patientId = patientId;
        this.doctorId = doctorId;
        this.notes = notes;
        this.diagnosis = diagnosis;
        this.createdAt = createdAt;
    }

    public UUID getId() { return id; }
    public UUID getAppointmentId() { return appointmentId; }
    public UUID getPatientId() { return patientId; }
    public UUID getDoctorId() { return doctorId; }
    public String getNotes() { return notes; }
    public String getDiagnosis() { return diagnosis; }
    public OffsetDateTime getCreatedAt() { return createdAt; }
}
