package com.vidya.health.adapters.out.persistence.jpa;

import jakarta.persistence.*;
import java.util.UUID;

@Entity
@Table(name = "doctors")
public class DoctorJpaEntity {

    @Id
    @Column(columnDefinition = "uuid")
    private UUID id;

    @Column(name="user_id", nullable=false, columnDefinition = "uuid", unique = true)
    private UUID userId;

    @Column(name="full_name", nullable=false, length = 140)
    private String fullName;

    @Column(nullable=false, length = 120)
    private String specialization;

    protected DoctorJpaEntity() {}

    public DoctorJpaEntity(UUID id, UUID userId, String fullName, String specialization) {
        this.id = id;
        this.userId = userId;
        this.fullName = fullName;
        this.specialization = specialization;
    }

    public UUID getId() { return id; }
    public UUID getUserId() { return userId; }
    public String getFullName() { return fullName; }
    public String getSpecialization() { return specialization; }
}
