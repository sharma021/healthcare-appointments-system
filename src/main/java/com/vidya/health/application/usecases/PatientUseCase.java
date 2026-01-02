package com.vidya.health.application.usecases;

import com.vidya.health.application.ports.AuditPort;
import com.vidya.health.application.ports.PatientPort;
import com.vidya.health.application.usecases.dto.PatientView;
import com.vidya.health.domain.common.DomainException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class PatientUseCase {

    private final PatientPort patientPort;
    private final AuditPort auditPort;

    public PatientUseCase(PatientPort patientPort, AuditPort auditPort) {
        this.patientPort = patientPort;
        this.auditPort = auditPort;
    }

    public PatientView create(UUID userId, String fullName, String phone, String dobIso, String actorEmail) {
        PatientView p = patientPort.create(userId, fullName, phone, dobIso);
        auditPort.log("CREATE", "PATIENT", p.id(), actorEmail, "Created patient profile");
        return p;
    }

    public PatientView get(UUID id) {
        return patientPort.get(id).orElseThrow(() -> new DomainException("Patient not found"));
    }

    // ✅ existing non-paginated list (used by current controller)
    public List<PatientView> list() {
        return patientPort.list();
    }

    // ✅ new paginated list (used by updated controller)
    public Page<PatientView> list(Pageable pageable) {
        return patientPort.list(pageable);
    }

    public PatientView update(UUID patientId, String fullName, String phone, String dobIso, String actorEmail) {
        PatientView p = patientPort.update(patientId, fullName, phone, dobIso);
        auditPort.log("UPDATE", "PATIENT", p.id(), actorEmail, "Updated patient profile");
        return p;
    }

    public void delete(UUID patientId, String actorEmail) {
        patientPort.delete(patientId);
        auditPort.log("DELETE", "PATIENT", patientId, actorEmail, "Deleted patient profile");
    }
}
