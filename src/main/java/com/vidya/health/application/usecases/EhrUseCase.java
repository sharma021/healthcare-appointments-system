package com.vidya.health.application.usecases;

import com.vidya.health.application.ports.*;
import com.vidya.health.application.usecases.dto.EhrView;
import com.vidya.health.domain.common.DomainException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class EhrUseCase {

    private final EhrPort ehrPort;
    private final AppointmentPort appointmentPort;
    private final PatientPort patientPort;
    private final DoctorPort doctorPort;
    private final AuditPort auditPort;

    public EhrUseCase(EhrPort ehrPort, AppointmentPort appointmentPort, PatientPort patientPort, DoctorPort doctorPort, AuditPort auditPort) {
        this.ehrPort = ehrPort;
        this.appointmentPort = appointmentPort;
        this.patientPort = patientPort;
        this.doctorPort = doctorPort;
        this.auditPort = auditPort;
    }

    public EhrView add(UUID appointmentId, UUID patientId, UUID doctorId, String notes, String diagnosis, String actorEmail) {
        appointmentPort.get(appointmentId).orElseThrow(() -> new DomainException("Appointment not found"));
        patientPort.get(patientId).orElseThrow(() -> new DomainException("Patient not found"));
        doctorPort.get(doctorId).orElseThrow(() -> new DomainException("Doctor not found"));

        EhrView r = ehrPort.addRecord(appointmentId, patientId, doctorId, notes, diagnosis);
        auditPort.log("CREATE", "EHR", r.id(), actorEmail, "Added EHR record");
        return r;
    }

    public List<EhrView> listForPatient(UUID patientId) {
        return ehrPort.listForPatient(patientId);
    }
}
