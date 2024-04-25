package com.presto.Presto.Med.services.appointment.validations;

import com.presto.Presto.Med.domain.appointment.AppointmentRegisterDTO;
import com.presto.Presto.Med.infra.exceptions.InvalidScheduleException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.time.LocalDateTime;

@Component
public class ValidateInAdvance implements RegisterValidations{

    @Value("${schedule.time.in.advance}")
    private int scheduleTimeInAdvance;

    @Override
    public void validate(AppointmentRegisterDTO dto) {
        LocalDateTime now = LocalDateTime.now();

        if(Duration.between(now, dto.getDate()).toHours() <= scheduleTimeInAdvance){
            throw new InvalidScheduleException("You must schedule " + scheduleTimeInAdvance + " hours in advance.");
        }

    }
}
