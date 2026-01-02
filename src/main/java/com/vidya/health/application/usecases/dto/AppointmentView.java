package com.vidya.health.application.usecases.dto;

import java.time.OffsetDateTime;
import java.util.UUID;

public record AppointmentView(
        UUID id,
        UUID patientId,
        UUID doctorId,
        OffsetDateTime startTime,
        int durationMinutes,
        String reason,
        String status
) { }
