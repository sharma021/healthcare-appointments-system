package com.vidya.health.adapters.out.persistence;

import com.vidya.health.adapters.out.persistence.jpa.DoctorJpaEntity;
import com.vidya.health.application.ports.DoctorPort;
import com.vidya.health.application.usecases.dto.DoctorView;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Component
@Transactional
public class DoctorPersistenceAdapter implements DoctorPort {

    private final DoctorJpaRepository repo;

    public DoctorPersistenceAdapter(DoctorJpaRepository repo) {
        this.repo = repo;
    }

    @Override
    public DoctorView create(UUID userId, String fullName, String specialization) {
        DoctorJpaEntity e = new DoctorJpaEntity(UUID.randomUUID(), userId, fullName, specialization);
        return Mapper.toView(repo.save(e));
    }

    @Override
    public Optional<DoctorView> get(UUID doctorId) {
        return repo.findById(doctorId).map(Mapper::toView);
    }

    @Override
    public List<DoctorView> list() {
        return repo.findAll().stream().map(Mapper::toView).toList();
    }

    @Override
    public Optional<DoctorView> findByUserId(UUID userId) {
        return repo.findByUserId(userId).map(Mapper::toView);
    }
}
