package com.vidya.health.application.usecases;

import com.vidya.health.adapters.out.persistence.AppointmentJpaRepository;
import org.springframework.stereotype.Service;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.Map;

@Service
public class AnalyticsUseCase {

    private final AppointmentJpaRepository appointmentJpaRepository;

    public AnalyticsUseCase(AppointmentJpaRepository appointmentJpaRepository) {
        this.appointmentJpaRepository = appointmentJpaRepository;
    }

    public List<Map<String, Object>> appointmentCountsByDoctor(OffsetDateTime from, OffsetDateTime to) {
        return appointmentJpaRepository.countByDoctorBetween(from, to);
    }
}
