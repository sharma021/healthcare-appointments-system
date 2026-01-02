package com.vidya.health.application.usecases.dto;

import java.time.OffsetDateTime;
import java.util.UUID;

public record EhrView(UUID id, UUID appointmentId, UUID patientId, UUID doctorId, String notes, String diagnosis, OffsetDateTime createdAt) { }
