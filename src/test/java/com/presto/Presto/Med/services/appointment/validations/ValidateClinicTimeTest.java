package com.presto.Presto.Med.services.appointment.validations;

import com.presto.Presto.Med.domain.appointment.AppointmentRegisterDTO;
import com.presto.Presto.Med.infra.exceptions.InvalidScheduleException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.util.ReflectionTestUtils;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class ValidateClinicTimeTest {

    @InjectMocks
    private ValidateClinicTime validateClinicTime;

    private final AppointmentRegisterDTO dto =
            new AppointmentRegisterDTO(1L, null, 1L, null);

    @Test
    void RightClinicTimeTest(){
        ReflectionTestUtils.setField(validateClinicTime, "openingHour", 9);
        ReflectionTestUtils.setField(validateClinicTime, "closingHour", 17);

        LocalDateTime goodTime =  LocalDateTime.of(2025, 1, 15, 13, 0);

        dto.setDate(goodTime);

        Assertions.assertDoesNotThrow(() -> validateClinicTime.validate(dto));
    }

    @Test
    void WrongClinicTimeTest(){
        ReflectionTestUtils.setField(validateClinicTime, "openingHour", 9);
        ReflectionTestUtils.setField(validateClinicTime, "closingHour", 17);

        LocalDateTime wrongTime =  LocalDateTime.now().plusMonths(1).withHour(8);

        dto.setDate(wrongTime);

        Assertions.assertThrows(InvalidScheduleException.class, () -> validateClinicTime.validate(dto));
    }

}