package com.vidya.health.adapters.out.persistence;

import com.vidya.health.adapters.out.persistence.jpa.PatientJpaEntity;
import com.vidya.health.application.ports.PatientPort;
import com.vidya.health.application.usecases.dto.PatientView;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Component
@Transactional
public class PatientPersistenceAdapter implements PatientPort {

    private final PatientJpaRepository repo;

    public PatientPersistenceAdapter(PatientJpaRepository repo) {
        this.repo = repo;
    }

    // ✅ Pagination-enabled list
    @Override
    public Page<PatientView> list(Pageable pageable) {
        return repo.findAll(pageable)
                .map(Mapper::toView);
    }

    @Override
    public PatientView create(UUID userId, String fullName, String phone, String dobIso) {
        PatientJpaEntity e = new PatientJpaEntity(UUID.randomUUID(), userId, fullName, phone, dobIso);
        return Mapper.toView(repo.save(e));
    }

    @Override
    public Optional<PatientView> get(UUID patientId) {
        return repo.findById(patientId).map(Mapper::toView);
    }

    @Override
    public List<PatientView> list() {
        return repo.findAll().stream().map(Mapper::toView).toList();
    }

    @Override
    public PatientView update(UUID patientId, String fullName, String phone, String dobIso) {
        PatientJpaEntity e = repo.findById(patientId).orElseThrow();
        e.setFullName(fullName);
        e.setPhone(phone);
        e.setDobIso(dobIso);
        return Mapper.toView(repo.save(e));
    }

    @Override
    public void delete(UUID patientId) {
        repo.deleteById(patientId);
    }

    @Override
    public Optional<PatientView> findByUserId(UUID userId) {
        return repo.findByUserId(userId).map(Mapper::toView);
    }
}
