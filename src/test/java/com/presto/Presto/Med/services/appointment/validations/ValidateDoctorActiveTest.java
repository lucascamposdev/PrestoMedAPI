package com.presto.Presto.Med.services.appointment.validations;

import com.presto.Presto.Med.domain.appointment.AppointmentRegisterDTO;
import com.presto.Presto.Med.infra.exceptions.InvalidScheduleException;
import com.presto.Presto.Med.repositories.DoctorRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.BDDMockito;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class ValidateDoctorActiveTest {

    @InjectMocks
    private ValidateDoctorActive validateDoctorActive;

    @Mock
    private DoctorRepository doctorRepository;

    @Mock
    private AppointmentRegisterDTO dto;

    @Test
    @DisplayName("shouldn't return exception if doctor is active")
    void DoctorIsActiveTest(){

        //arrange & act
        BDDMockito.given(doctorRepository.checkIfDoctorIsActive(dto.getDoctorId()))
                .willReturn(true);


        //assert
        Assertions.assertDoesNotThrow(() -> validateDoctorActive.validate(dto));
    }

    @Test
    @DisplayName("should return exception if doctor is unactive")
    void DoctorIsUnactiveTest(){

        //arrange & act
        BDDMockito.given(doctorRepository.checkIfDoctorIsActive(dto.getDoctorId()))
                .willReturn(false);


        //assert
        Assertions.assertThrows(InvalidScheduleException.class, () -> validateDoctorActive.validate(dto));
    }

}