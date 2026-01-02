package com.vidya.health.config;

import com.vidya.health.adapters.out.persistence.*;
import com.vidya.health.adapters.out.persistence.jpa.*;
import com.vidya.health.domain.user.Role;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.UUID;

@Configuration
public class DataInitializer {

    @Bean
    CommandLineRunner seed(UserJpaRepository userRepo,
                           DoctorJpaRepository doctorRepo,
                           PatientJpaRepository patientRepo,
                           PasswordEncoder encoder) {
        return args -> {
            if (userRepo.count() > 0) return;

            UUID adminId = UUID.randomUUID();
            userRepo.save(new UserJpaEntity(adminId, "admin@demo.com", encoder.encode("Admin@123"), Role.ADMIN));

            UUID doctorUserId = UUID.randomUUID();
            userRepo.save(new UserJpaEntity(doctorUserId, "doctor@demo.com", encoder.encode("Doctor@123"), Role.DOCTOR));
            doctorRepo.save(new DoctorJpaEntity(UUID.randomUUID(), doctorUserId, "Dr. A Sharma", "General Medicine"));

            UUID patientUserId = UUID.randomUUID();
            userRepo.save(new UserJpaEntity(patientUserId, "patient@demo.com", encoder.encode("Patient@123"), Role.PATIENT));
            patientRepo.save(new PatientJpaEntity(UUID.randomUUID(), patientUserId, "Vidya Patient", "9999999999", "1999-01-01"));
        };
    }
}
