package com.presto.Presto.Med.services.appointment.validations;

import com.presto.Presto.Med.domain.appointment.AppointmentRegisterDTO;
import com.presto.Presto.Med.infra.exceptions.InvalidScheduleException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class ValidateClinicTime implements RegisterValidations{

    @Value("${clinic.opening.hours}")
    private int openingHour;

    @Value("${clinic.closing.hours}")
    private int closingHour;

    @Override
    public void validate(AppointmentRegisterDTO dto) {
        int hour = dto.getDate().getHour();

        if(!(hour >= openingHour && hour <= (closingHour - 1))){
            throw new InvalidScheduleException(
                    "You must schedule between " + openingHour + ":00" +
                    " and " + closingHour + ":00.");
        }
    }
}
