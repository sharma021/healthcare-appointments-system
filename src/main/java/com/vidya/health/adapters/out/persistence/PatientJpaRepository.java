package com.vidya.health.adapters.out.persistence;

import com.vidya.health.adapters.out.persistence.jpa.PatientJpaEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface PatientJpaRepository extends JpaRepository<PatientJpaEntity, UUID> {
    Optional<PatientJpaEntity> findByUserId(UUID userId);
}
