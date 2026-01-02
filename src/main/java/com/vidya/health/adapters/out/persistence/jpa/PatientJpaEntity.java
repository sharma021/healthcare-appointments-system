package com.vidya.health.adapters.out.persistence.jpa;

import jakarta.persistence.*;
import java.util.UUID;

@Entity
@Table(name = "patients")
public class PatientJpaEntity {

    @Id
    @Column(columnDefinition = "uuid")
    private UUID id;

    @Column(name="user_id", nullable=false, columnDefinition = "uuid", unique = true)
    private UUID userId;

    @Column(name="full_name", nullable=false, length = 140)
    private String fullName;

    @Column(length = 30)
    private String phone;

    @Column(name="dob_iso", length = 20)
    private String dobIso;

    protected PatientJpaEntity() {}

    public PatientJpaEntity(UUID id, UUID userId, String fullName, String phone, String dobIso) {
        this.id = id;
        this.userId = userId;
        this.fullName = fullName;
        this.phone = phone;
        this.dobIso = dobIso;
    }

    public UUID getId() { return id; }
    public UUID getUserId() { return userId; }
    public String getFullName() { return fullName; }
    public String getPhone() { return phone; }
    public String getDobIso() { return dobIso; }

    public void setFullName(String fullName) { this.fullName = fullName; }
    public void setPhone(String phone) { this.phone = phone; }
    public void setDobIso(String dobIso) { this.dobIso = dobIso; }
}
