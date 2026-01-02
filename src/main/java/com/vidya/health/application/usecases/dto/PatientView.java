package com.vidya.health.application.usecases.dto;

import java.util.UUID;

public record PatientView(UUID id, UUID userId, String fullName, String phone, String dobIso) { }
