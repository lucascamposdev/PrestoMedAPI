package com.presto.Presto.Med.services.doctor;

import com.presto.Presto.Med.domain.doctor.Doctor;
import com.presto.Presto.Med.domain.doctor.DoctorRegisterDTO;
import com.presto.Presto.Med.enums.DoctorSpecialties;
import com.presto.Presto.Med.infra.exceptions.DataAlreadyExists;
import com.presto.Presto.Med.repositories.DoctorRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class DoctorServiceTest {

    @InjectMocks
    DoctorService doctorService;

    @Mock
    DoctorRepository doctorRepository;

    @Captor
    ArgumentCaptor<Doctor> doctorCaptor;

    private final DoctorRegisterDTO dto = new DoctorRegisterDTO(
            "Lucas",
            "Campos",
            "150196",
            DoctorSpecialties.CARDIOLOGY
    );

    @Test
    void shouldReturnDoctorWhenRegister() {
//        ARRANGE
        BDDMockito.given(doctorRepository.findByCrm(dto.getCrm())).willReturn(null);

//        ACT
        doctorService.create(dto);

//        ASSERT
        BDDMockito.then(doctorRepository).should().save(doctorCaptor.capture());
        Doctor createdDoctor = doctorCaptor.getValue();

        Assertions.assertEquals(createdDoctor.getCrm(), dto.getCrm());
        Assertions.assertEquals(createdDoctor.getName(), dto.getName());
        Assertions.assertEquals(createdDoctor.getLast_name(), dto.getLast_name());
        Assertions.assertEquals(createdDoctor.getSpecialty(), dto.getSpecialty());
    }

    @Test
    void throwExceptionWhenCrmAlreadyExists() {
//        ARRANGE
        BDDMockito.given(doctorRepository.findByCrm("150196")).willReturn(new Doctor());

//        ACT
        Assertions.assertThrows(DataAlreadyExists.class, () -> doctorService.create(dto));
    }
}