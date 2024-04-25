package com.presto.Presto.Med.services.appointment.validations;

import com.presto.Presto.Med.domain.appointment.Appointment;
import com.presto.Presto.Med.domain.appointment.AppointmentRegisterDTO;
import com.presto.Presto.Med.infra.exceptions.DataAlreadyExists;
import com.presto.Presto.Med.infra.exceptions.InvalidScheduleException;
import com.presto.Presto.Med.repositories.AppointmentRepository;
import com.presto.Presto.Med.repositories.DoctorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


@Component
public class ValidateDoctorSchedule implements RegisterValidations{

    @Autowired
    private AppointmentRepository appointmentRepository;

    @Override
    public void validate(AppointmentRegisterDTO dto) {
        boolean alreadyExists = appointmentRepository
                .existsByDoctorIdAndDate(dto.getDoctorId(), dto.getDate());

        if(alreadyExists){
            throw new InvalidScheduleException("This time isn't available.");
        }
    }
}
