package com.presto.Presto.Med.services.appointment.validations;

import com.presto.Presto.Med.domain.appointment.AppointmentRegisterDTO;

public interface RegisterValidations {

    void validate(AppointmentRegisterDTO dto);
}
