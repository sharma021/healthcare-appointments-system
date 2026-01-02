package com.vidya.health.adapters.out.persistence.jpa;

import jakarta.persistence.*;
import java.time.OffsetDateTime;
import java.util.UUID;

@Entity
@Table(name="audit_logs", indexes = {@Index(name="idx_audit_entity", columnList = "entity_type,entity_id")})
public class AuditLogJpaEntity {

    @Id
    @Column(columnDefinition = "uuid")
    private UUID id;

    @Column(nullable=false, length=30)
    private String action;

    @Column(name="entity_type", nullable=false, length=60)
    private String entityType;

    @Column(name="entity_id", nullable=false, columnDefinition = "uuid")
    private UUID entityId;

    @Column(name="actor_email", length=120)
    private String actorEmail;

    @Column(columnDefinition = "text")
    private String details;

    @Column(name="created_at", nullable=false)
    private OffsetDateTime createdAt;

    protected AuditLogJpaEntity() {}

    public AuditLogJpaEntity(UUID id, String action, String entityType, UUID entityId, String actorEmail, String details, OffsetDateTime createdAt) {
        this.id = id;
        this.action = action;
        this.entityType = entityType;
        this.entityId = entityId;
        this.actorEmail = actorEmail;
        this.details = details;
        this.createdAt = createdAt;
    }
}
