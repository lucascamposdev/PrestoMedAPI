package com.presto.Presto.Med.services.appointment.validations;

import com.presto.Presto.Med.domain.appointment.AppointmentRegisterDTO;
import com.presto.Presto.Med.infra.exceptions.InvalidScheduleException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.BDDMockito;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.util.ReflectionTestUtils;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class ValidateInAdvanceTest {

    @InjectMocks
    ValidateInAdvance validateInAdvance;

    int timeInAdvance = 24; //hours

    LocalDateTime goodTestTime = LocalDateTime.now().plusHours(48);
    LocalDateTime badTestTime = LocalDateTime.now();


    @Test
    void RightScheduleInAdvanceTime(){
        // ARRANGE
        ReflectionTestUtils.setField(validateInAdvance, "scheduleTimeInAdvance", timeInAdvance);

        AppointmentRegisterDTO dto =
                new AppointmentRegisterDTO(1L, 1L, goodTestTime);

        // ACT
        Assertions.assertDoesNotThrow(() -> validateInAdvance.validate(dto));
    }

    @Test
    void WrongScheduleInAdvanceTime(){
        // ARRANGE
        ReflectionTestUtils.setField(validateInAdvance, "scheduleTimeInAdvance", timeInAdvance);

        AppointmentRegisterDTO dto =
                new AppointmentRegisterDTO(1L, 1L, badTestTime);

        // ACT
        Assertions.assertThrows(InvalidScheduleException.class, () -> validateInAdvance.validate(dto));
    }


}