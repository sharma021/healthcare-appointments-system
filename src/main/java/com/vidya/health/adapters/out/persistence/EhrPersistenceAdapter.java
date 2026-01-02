package com.vidya.health.adapters.out.persistence;

import com.vidya.health.adapters.out.persistence.jpa.EhrJpaEntity;
import com.vidya.health.application.ports.EhrPort;
import com.vidya.health.application.usecases.dto.EhrView;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.UUID;

@Component
@Transactional
public class EhrPersistenceAdapter implements EhrPort {

    private final EhrJpaRepository repo;

    public EhrPersistenceAdapter(EhrJpaRepository repo) {
        this.repo = repo;
    }

    @Override
    public EhrView addRecord(UUID appointmentId, UUID patientId, UUID doctorId, String notes, String diagnosis) {
        EhrJpaEntity e = new EhrJpaEntity(UUID.randomUUID(), appointmentId, patientId, doctorId, notes, diagnosis, OffsetDateTime.now());
        return Mapper.toView(repo.save(e));
    }

    @Override
    public List<EhrView> listForPatient(UUID patientId) {
        return repo.findByPatientIdOrderByCreatedAtDesc(patientId).stream().map(Mapper::toView).toList();
    }
}
