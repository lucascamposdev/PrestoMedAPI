package com.presto.Presto.Med.services.appointment.validations;

import com.presto.Presto.Med.domain.appointment.AppointmentRegisterDTO;
import com.presto.Presto.Med.infra.exceptions.InvalidScheduleException;
import com.presto.Presto.Med.repositories.AppointmentRepository;
import com.presto.Presto.Med.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ValidateUserSchedule implements RegisterValidations{

    @Autowired
    private AppointmentRepository appointmentRepository;

    @Override
    public void validate(AppointmentRegisterDTO dto) {
        if(appointmentRepository.existsByUserIdAndDate(dto.getUserId(), dto.getDate())){
            throw new InvalidScheduleException("user already have an appointment that time.");
        }
    }
}
