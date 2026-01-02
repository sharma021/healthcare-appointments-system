package com.vidya.health.adapters.out.persistence;

import com.vidya.health.adapters.out.persistence.jpa.AppointmentJpaEntity;
import com.vidya.health.adapters.out.persistence.jpa.AppointmentStatus;
import com.vidya.health.application.ports.AppointmentPort;
import com.vidya.health.application.usecases.dto.AppointmentView;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Component
@Transactional
public class AppointmentPersistenceAdapter implements AppointmentPort {

    private final AppointmentJpaRepository repo;

    public AppointmentPersistenceAdapter(AppointmentJpaRepository repo) {
        this.repo = repo;
    }

    // ✅ Pagination-enabled methods (new)
    @Override
    public Page<AppointmentView> listForPatient(UUID patientId, Pageable pageable) {
        return repo.findByPatientId(patientId, pageable)
                .map(Mapper::toView);
    }

    @Override
    public Page<AppointmentView> listForDoctor(UUID doctorId, Pageable pageable) {
        return repo.findByDoctorId(doctorId, pageable)
                .map(Mapper::toView);
    }

    @Override
    public AppointmentView create(UUID patientId, UUID doctorId, OffsetDateTime startTime, int durationMinutes, String reason) {
        AppointmentJpaEntity e = new AppointmentJpaEntity(
                UUID.randomUUID(),
                patientId,
                doctorId,
                startTime,
                durationMinutes,
                reason,
                AppointmentStatus.SCHEDULED
        );
        return Mapper.toView(repo.save(e));
    }

    @Override
    public Optional<AppointmentView> get(UUID appointmentId) {
        return repo.findById(appointmentId).map(Mapper::toView);
    }

    @Override
    public List<AppointmentView> listForPatient(UUID patientId) {
        return repo.findByPatientIdOrderByStartTimeDesc(patientId)
                .stream()
                .map(Mapper::toView)
                .toList();
    }

    @Override
    public List<AppointmentView> listForDoctor(UUID doctorId) {
        return repo.findByDoctorIdOrderByStartTimeDesc(doctorId)
                .stream()
                .map(Mapper::toView)
                .toList();
    }

    @Override
    public AppointmentView updateStatus(UUID appointmentId, String status) {
        AppointmentJpaEntity e = repo.findById(appointmentId).orElseThrow();
        e.setStatus(AppointmentStatus.valueOf(status));
        return Mapper.toView(repo.save(e));
    }

    @Override
    public boolean doctorHasOverlap(UUID doctorId, OffsetDateTime startTime, OffsetDateTime endTime) {
        OffsetDateTime from = startTime.minusHours(12);
        OffsetDateTime to = endTime.plusHours(12);

        List<AppointmentJpaEntity> list = repo.findDoctorAppointmentsInWindow(
                doctorId, from, to, AppointmentStatus.CANCELLED
        );

        for (AppointmentJpaEntity a : list) {
            OffsetDateTime aStart = a.getStartTime();
            OffsetDateTime aEnd = aStart.plusMinutes(a.getDurationMinutes());
            boolean overlap = aStart.isBefore(endTime) && aEnd.isAfter(startTime);
            if (overlap) return true;
        }
        return false;
    }
}
