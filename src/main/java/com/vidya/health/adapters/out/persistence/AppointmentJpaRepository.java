package com.vidya.health.adapters.out.persistence;

import com.vidya.health.adapters.out.persistence.jpa.AppointmentJpaEntity;
import com.vidya.health.adapters.out.persistence.jpa.AppointmentStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public interface AppointmentJpaRepository extends JpaRepository<AppointmentJpaEntity, UUID> {

    List<AppointmentJpaEntity> findByPatientIdOrderByStartTimeDesc(UUID patientId);
    List<AppointmentJpaEntity> findByDoctorIdOrderByStartTimeDesc(UUID doctorId);

    Page<AppointmentJpaEntity> findByPatientId(UUID patientId, Pageable pageable);
    Page<AppointmentJpaEntity> findByDoctorId(UUID doctorId, Pageable pageable);

    @Query("select a from AppointmentJpaEntity a where a.doctorId = :doctorId and a.status <> :cancelled and a.startTime >= :from and a.startTime <= :to order by a.startTime asc")
    List<AppointmentJpaEntity> findDoctorAppointmentsInWindow(@Param("doctorId") UUID doctorId,
                                                              @Param("from") OffsetDateTime from,
                                                              @Param("to") OffsetDateTime to,
                                                              @Param("cancelled") AppointmentStatus cancelled);

    @Query("select new map(a.doctorId as doctorId, count(a) as count) from AppointmentJpaEntity a where a.startTime >= :from and a.startTime <= :to group by a.doctorId order by count(a) desc")
    List<Map<String, Object>> countByDoctorBetween(@Param("from") OffsetDateTime from, @Param("to") OffsetDateTime to);
}
