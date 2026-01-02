package com.vidya.health.adapters.out.persistence;

import com.vidya.health.adapters.out.persistence.jpa.AuditLogJpaEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface AuditLogJpaRepository extends JpaRepository<AuditLogJpaEntity, UUID> { }
