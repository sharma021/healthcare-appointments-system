package com.vidya.health.application.ports;

import com.vidya.health.application.usecases.dto.DoctorView;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface DoctorPort {
    DoctorView create(UUID userId, String fullName, String specialization);
    Optional<DoctorView> get(UUID doctorId);
    List<DoctorView> list();
    Optional<DoctorView> findByUserId(UUID userId);
}
