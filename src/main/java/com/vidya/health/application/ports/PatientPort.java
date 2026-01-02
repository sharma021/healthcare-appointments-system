package com.vidya.health.application.ports;

import com.vidya.health.application.usecases.dto.PatientView;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface PatientPort {

    PatientView create(UUID userId, String fullName, String phone, String dobIso);

    Optional<PatientView> get(UUID patientId);

    List<PatientView> list();

    // ✅ Paginated list (fixes your error)
    Page<PatientView> list(Pageable pageable);

    PatientView update(UUID patientId, String fullName, String phone, String dobIso);

    void delete(UUID patientId);

    Optional<PatientView> findByUserId(UUID userId);
}
