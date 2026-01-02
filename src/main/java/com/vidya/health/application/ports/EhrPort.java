package com.vidya.health.application.ports;

import com.vidya.health.application.usecases.dto.EhrView;

import java.util.List;
import java.util.UUID;

public interface EhrPort {
    EhrView addRecord(UUID appointmentId, UUID patientId, UUID doctorId, String notes, String diagnosis);
    List<EhrView> listForPatient(UUID patientId);
}
