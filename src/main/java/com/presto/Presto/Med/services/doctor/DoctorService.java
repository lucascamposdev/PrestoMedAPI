package com.presto.Presto.Med.services.doctor;

import com.presto.Presto.Med.domain.doctor.Doctor;
import com.presto.Presto.Med.domain.doctor.DoctorMinimalResponseDTO;
import com.presto.Presto.Med.domain.doctor.DoctorRegisterDTO;
import com.presto.Presto.Med.domain.doctor.DoctorReponseDTO;
import com.presto.Presto.Med.enums.DoctorSpecialties;
import com.presto.Presto.Med.infra.exceptions.DataAlreadyExists;
import com.presto.Presto.Med.repositories.DoctorRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
public class DoctorService {

    @Autowired
    private DoctorRepository doctorRepository;

    @Transactional
    public Doctor create(DoctorRegisterDTO dto){
        if(doctorRepository.findByCrm(dto.getCrm()) != null){
            throw new DataAlreadyExists("crm already registered.");
        }

        return doctorRepository.save(new Doctor(dto));
    }

    public Doctor findById(Long id){
        return doctorRepository.findByIdWithAppointments(id)
                .orElseThrow(() -> new EntityNotFoundException("doctor not found"));
    }

    public Page<Doctor> listAllDoctors(Pageable page){
        return doctorRepository.findAll(page);
    }

    public Page<Doctor> findBySpecialty(String specialty, Pageable page){
        try{
            DoctorSpecialties foundSpecialty = DoctorSpecialties.valueOf(specialty.toUpperCase());

        return doctorRepository
                .findBySpecialty(page, foundSpecialty);

        }catch (IllegalArgumentException ex){
            throw new IllegalArgumentException("specialty not found.");
        }
    }

    @Transactional
    public Doctor changeActiveStatus(Long id){
        Doctor foundDoctor = doctorRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("doctor not found"));

        foundDoctor.setActive(!foundDoctor.getActive());

        return foundDoctor;
    }

    public void deleteDoctor(Long id) {
        if (!doctorRepository.existsById(id)) {
            throw new EntityNotFoundException("doctor not found");
        }

        doctorRepository.deleteById(id);
    }

    public Doctor randomAvailableActiveDoctorBySpecialty(DoctorSpecialties specialty, LocalDateTime date){
        return doctorRepository
                .randomAvailableActiveDoctorBySpecialty(specialty, date)
                .orElseThrow(() -> new EntityNotFoundException("no doctors available that time."));
    }
}
