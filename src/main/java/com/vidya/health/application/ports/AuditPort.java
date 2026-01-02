package com.vidya.health.application.ports;

import java.util.UUID;

public interface AuditPort {
    void log(String action, String entityType, UUID entityId, String actorEmail, String details);
}
