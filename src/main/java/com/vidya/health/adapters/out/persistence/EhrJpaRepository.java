package com.vidya.health.adapters.out.persistence;

import com.vidya.health.adapters.out.persistence.jpa.EhrJpaEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface EhrJpaRepository extends JpaRepository<EhrJpaEntity, UUID> {
    List<EhrJpaEntity> findByPatientIdOrderByCreatedAtDesc(UUID patientId);
}
