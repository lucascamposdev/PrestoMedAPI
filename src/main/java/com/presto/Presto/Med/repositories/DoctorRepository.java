package com.presto.Presto.Med.repositories;

import com.presto.Presto.Med.domain.doctor.Doctor;
import com.presto.Presto.Med.enums.DoctorSpecialties;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import javax.print.Doc;
import java.util.List;
import java.util.Optional;

public interface DoctorRepository extends JpaRepository<Doctor, Long> {

    Doctor findByCrm(String crm);

    @Query("SELECT d FROM Doctor d LEFT JOIN FETCH d.appointments WHERE d.id = :id")
    Optional<Doctor> findByIdWithAppointments(Long id);

    @Query("SELECT d.active FROM Doctor d WHERE d.id = :doctorId")
    boolean checkIfDoctorIsActive(Long doctorId);

    Page<Doctor> findBySpecialty(Pageable page, DoctorSpecialties specialty);
}
