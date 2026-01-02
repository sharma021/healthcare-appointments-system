package com.vidya.health.adapters.out.persistence;

import com.vidya.health.adapters.out.persistence.jpa.DoctorJpaEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface DoctorJpaRepository extends JpaRepository<DoctorJpaEntity, UUID> {
    Optional<DoctorJpaEntity> findByUserId(UUID userId);
}
