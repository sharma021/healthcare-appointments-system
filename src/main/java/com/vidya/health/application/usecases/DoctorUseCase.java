package com.vidya.health.application.usecases;

import com.vidya.health.application.ports.AuditPort;
import com.vidya.health.application.ports.DoctorPort;
import com.vidya.health.application.usecases.dto.DoctorView;
import com.vidya.health.domain.common.DomainException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class DoctorUseCase {

    private final DoctorPort doctorPort;
    private final AuditPort auditPort;

    public DoctorUseCase(DoctorPort doctorPort, AuditPort auditPort) {
        this.doctorPort = doctorPort;
        this.auditPort = auditPort;
    }

    public DoctorView create(UUID userId, String fullName, String specialization, String actorEmail) {
        DoctorView d = doctorPort.create(userId, fullName, specialization);
        auditPort.log("CREATE", "DOCTOR", d.id(), actorEmail, "Created doctor profile");
        return d;
    }

    public DoctorView get(UUID id) {
        return doctorPort.get(id).orElseThrow(() -> new DomainException("Doctor not found"));
    }

    public List<DoctorView> list() { return doctorPort.list(); }
}
