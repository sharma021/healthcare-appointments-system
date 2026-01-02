package com.vidya.health.application.usecases.dto;

import com.vidya.health.domain.user.Role;

import java.util.UUID;

public record UserView(UUID id, String email, Role role) { }
