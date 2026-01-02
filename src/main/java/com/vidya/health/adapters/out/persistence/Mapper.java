package com.vidya.health.adapters.out.persistence;

import com.vidya.health.adapters.out.persistence.jpa.*;
import com.vidya.health.application.usecases.dto.*;

public final class Mapper {
    private Mapper() {}

    public static UserView toView(UserJpaEntity e) {
        return new UserView(e.getId(), e.getEmail(), e.getRole());
    }

    public static PatientView toView(PatientJpaEntity e) {
        return new PatientView(e.getId(), e.getUserId(), e.getFullName(), e.getPhone(), e.getDobIso());
    }

    public static DoctorView toView(DoctorJpaEntity e) {
        return new DoctorView(e.getId(), e.getUserId(), e.getFullName(), e.getSpecialization());
    }

    public static AppointmentView toView(AppointmentJpaEntity e) {
        return new AppointmentView(e.getId(), e.getPatientId(), e.getDoctorId(), e.getStartTime(), e.getDurationMinutes(), e.getReason(), e.getStatus().name());
    }

    public static EhrView toView(EhrJpaEntity e) {
        return new EhrView(e.getId(), e.getAppointmentId(), e.getPatientId(), e.getDoctorId(), e.getNotes(), e.getDiagnosis(), e.getCreatedAt());
    }
}
