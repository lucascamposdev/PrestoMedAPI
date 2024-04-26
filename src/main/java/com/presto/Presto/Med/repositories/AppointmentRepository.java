package com.presto.Presto.Med.repositories;

import com.presto.Presto.Med.domain.appointment.Appointment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDateTime;
import java.util.Optional;

public interface AppointmentRepository extends JpaRepository<Appointment, Long> {

    boolean existsByDoctorIdAndDate(Long doctor_id, LocalDateTime date);
    boolean existsByUserIdAndDate(Long user_id, LocalDateTime date);
}
