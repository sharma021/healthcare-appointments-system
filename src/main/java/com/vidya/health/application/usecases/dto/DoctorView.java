package com.vidya.health.application.usecases.dto;

import java.util.UUID;

public record DoctorView(UUID id, UUID userId, String fullName, String specialization) { }
