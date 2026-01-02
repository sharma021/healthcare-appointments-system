package com.vidya.health.adapters.out.persistence;

import com.vidya.health.adapters.out.persistence.jpa.AuditLogJpaEntity;
import com.vidya.health.application.ports.AuditPort;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.OffsetDateTime;
import java.util.UUID;

@Component
@Transactional
public class AuditPersistenceAdapter implements AuditPort {

    private final AuditLogJpaRepository repo;

    public AuditPersistenceAdapter(AuditLogJpaRepository repo) {
        this.repo = repo;
    }

    @Override
    public void log(String action, String entityType, UUID entityId, String actorEmail, String details) {
        AuditLogJpaEntity e = new AuditLogJpaEntity(UUID.randomUUID(), action, entityType, entityId, actorEmail, details, OffsetDateTime.now());
        repo.save(e);
    }
}
