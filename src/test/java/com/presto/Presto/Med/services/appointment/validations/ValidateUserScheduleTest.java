package com.presto.Presto.Med.services.appointment.validations;

import com.presto.Presto.Med.domain.appointment.AppointmentRegisterDTO;
import com.presto.Presto.Med.infra.exceptions.InvalidScheduleException;
import com.presto.Presto.Med.repositories.AppointmentRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.BDDMockito;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class ValidateUserScheduleTest {

    @InjectMocks
    ValidateUserSchedule validateUserSchedule;

    @Mock
    AppointmentRepository appointmentRepository;

    private final AppointmentRegisterDTO dto =
            new AppointmentRegisterDTO(1L, null, 1L, LocalDateTime.now());

    @Test
    void UserScheduleIsAvailableTest(){
        // ARRANGE
        BDDMockito.given(appointmentRepository.existsByUserIdAndDate(dto.getUserId(), dto.getDate()))
                .willReturn(false);

        // ACT & ASSERT
        Assertions.assertDoesNotThrow(() -> validateUserSchedule.validate(dto));
    }

    @Test
    void UserScheduleIsNotAvailableTest(){
        // ARRANGE
        BDDMockito.given(appointmentRepository.existsByUserIdAndDate(dto.getUserId(), dto.getDate()))
                .willReturn(true);

        // ACT & ASSERT
        Assertions.assertThrows(InvalidScheduleException.class, () -> validateUserSchedule.validate(dto));
    }

}