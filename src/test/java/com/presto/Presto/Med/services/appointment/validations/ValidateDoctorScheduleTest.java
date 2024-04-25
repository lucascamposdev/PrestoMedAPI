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
class ValidateDoctorScheduleTest {

    @InjectMocks
    ValidateDoctorSchedule validateDoctorSchedule;

    @Mock
    AppointmentRepository appointmentRepository;

    private final AppointmentRegisterDTO dto =
            new AppointmentRegisterDTO(1L, 1L, LocalDateTime.now());

    @Test
    void DoctorScheduleIsAvailableTest(){
        // ARRANGE
        BDDMockito.given(appointmentRepository.existsByDoctorIdAndDate(dto.getDoctorId(), dto.getDate()))
                .willReturn(false);

        // ACT & ASSERT
        Assertions.assertDoesNotThrow(() -> validateDoctorSchedule.validate(dto));
    }

    @Test
    void DoctorScheduleIsNotAvailableTest(){
        // ARRANGE
        BDDMockito.given(appointmentRepository.existsByDoctorIdAndDate(dto.getDoctorId(), dto.getDate()))
                .willReturn(true);

        // ACT & ASSERT
        Assertions.assertThrows(InvalidScheduleException.class, () -> validateDoctorSchedule.validate(dto));
    }

}