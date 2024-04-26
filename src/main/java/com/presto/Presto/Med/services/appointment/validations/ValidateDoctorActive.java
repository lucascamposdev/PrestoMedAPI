package com.presto.Presto.Med.services.appointment.validations;

import com.presto.Presto.Med.domain.appointment.AppointmentRegisterDTO;
import com.presto.Presto.Med.infra.exceptions.InvalidScheduleException;
import com.presto.Presto.Med.repositories.DoctorRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ValidateDoctorActive implements RegisterValidations{

    @Autowired
    private DoctorRepository doctorRepository;


    @Override
    public void validate(AppointmentRegisterDTO dto) {

        if(dto.getDoctorId() == null) return;

        boolean isActive = doctorRepository.checkIfDoctorIsActive(dto.getDoctorId());

        if(!isActive){
            throw new InvalidScheduleException("This doctor's schedule is inactive for now.");
        }
    }
}
