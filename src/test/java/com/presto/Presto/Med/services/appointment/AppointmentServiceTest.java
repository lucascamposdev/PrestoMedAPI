package com.presto.Presto.Med.services.appointment;

import com.presto.Presto.Med.domain.appointment.Appointment;
import com.presto.Presto.Med.domain.appointment.AppointmentRegisterDTO;
import com.presto.Presto.Med.domain.appointment.AppointmentUpdateDTO;
import com.presto.Presto.Med.domain.doctor.Doctor;
import com.presto.Presto.Med.domain.user.User;
import com.presto.Presto.Med.enums.DoctorSpecialties;
import com.presto.Presto.Med.infra.exceptions.InvalidScheduleException;
import com.presto.Presto.Med.repositories.AppointmentRepository;
import com.presto.Presto.Med.repositories.DoctorRepository;
import com.presto.Presto.Med.repositories.UserRepository;
import com.presto.Presto.Med.services.appointment.validations.*;
import com.presto.Presto.Med.services.doctor.DoctorService;
import com.presto.Presto.Med.services.user.UserService;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;

import javax.print.Doc;
import java.lang.module.InvalidModuleDescriptorException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class AppointmentServiceTest {

    @InjectMocks
    AppointmentService appointmentService;

    @Mock
    private DoctorService doctorService;

    @Mock
    private UserService userService;

    @Mock
    private AppointmentRepository appointmentRepository;

    @Spy
    private List<RegisterValidations> validations = new ArrayList<>();

    @Mock
    private ValidateDoctorActive validator1;
    @Mock
    private ValidateClinicTime validator2;

    @Mock
    private ValidateInAdvance validator3;

    @Mock
    private ValidateDoctorSchedule validator4;

    @Captor
    private ArgumentCaptor<Appointment> appointmentCaptor;

    @Mock
    Doctor doctor;

    @Mock
    User user;

    LocalDateTime time = LocalDateTime.of(2025, 12, 5, 13, 0);

    private final AppointmentRegisterDTO dto =
            new AppointmentRegisterDTO(
                    1L,
                    null,
                    2L,
                    time);
    @Test
    void shouldReturnAppointmentWhenRegisterWithDoctorId(){
        // ARRANGE
        BDDMockito.given(doctorService.findById(dto.getDoctorId())).willReturn(doctor);
        BDDMockito.given(userService.findById(dto.getUserId())).willReturn(user);

        // ACT
        Appointment appointment = appointmentService.register(dto);

        // ASSERT
        BDDMockito.then(appointmentRepository).should().save(appointmentCaptor.capture());
        Appointment savedAppointment = appointmentCaptor.getValue();

        Assertions.assertEquals(doctor, savedAppointment.getDoctor());
        Assertions.assertEquals(user, savedAppointment.getUser());
        Assertions.assertEquals(time, savedAppointment.getDate());
    }

    @Test
    void shouldReturnAppointmentWhenRegisterWithoutDoctorId(){
        // ARRANGE
        dto.setDoctorId(null);
        dto.setSpecialty(DoctorSpecialties.CARDIOLOGY);

        BDDMockito.given(doctorService
                .randomAvailableActiveDoctorBySpecialty(dto.getSpecialty(), dto.getDate()))
                .willReturn(doctor);
        BDDMockito.given(userService.findById(dto.getUserId())).willReturn(user);

        // ACT
        Appointment appointment = appointmentService.register(dto);

        // ASSERT
        BDDMockito.then(appointmentRepository).should().save(appointmentCaptor.capture());
        Appointment savedAppointment = appointmentCaptor.getValue();

        Assertions.assertEquals(doctor, savedAppointment.getDoctor());
        Assertions.assertEquals(user, savedAppointment.getUser());
        Assertions.assertEquals(time, savedAppointment.getDate());
    }

    @Test
    void shouldRunValidationsWhenRegister(){
        // ARRANGE
        BDDMockito.given(doctorService.findById(dto.getDoctorId())).willReturn(doctor);
        BDDMockito.given(userService.findById(dto.getUserId())).willReturn(user);

        validations.add(validator1);
        validations.add(validator2);
        validations.add(validator3);
        validations.add(validator4);

        // ACT
        Appointment appointment = appointmentService.register(dto);

        // ASSERT
        BDDMockito.then(validator1).should().validate(dto);
        BDDMockito.then(validator2).should().validate(dto);
        BDDMockito.then(validator3).should().validate(dto);
        BDDMockito.then(validator4).should().validate(dto);
    }

    @Test
    void shouldUpdateAppointment(){
        // ARRANGE
        Long appointmentId = 1L;
        LocalDateTime newDate = LocalDateTime.now().plusDays(5);

        Appointment foundAppointment = new Appointment();
        foundAppointment.setId(appointmentId);
        foundAppointment.setDate(LocalDateTime.now());
        foundAppointment.setDoctor(doctor);
        foundAppointment.setUser(user);


        BDDMockito.given(appointmentRepository
                        .findById(appointmentId)).willReturn(Optional.of(foundAppointment));

        // ACT
        Appointment updatedAppointment = appointmentService
                .reschedule(appointmentId, new AppointmentUpdateDTO(newDate));

        // ASSERT
        assertNotNull(updatedAppointment);
        assertEquals(updatedAppointment.getDate(), newDate);
        assertEquals(updatedAppointment.getId(), appointmentId);

        verify(appointmentRepository, times(1)).findById(appointmentId);
    }

}