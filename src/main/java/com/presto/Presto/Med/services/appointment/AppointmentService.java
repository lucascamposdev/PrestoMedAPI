package com.presto.Presto.Med.services.appointment;

import com.presto.Presto.Med.domain.appointment.Appointment;
import com.presto.Presto.Med.domain.appointment.AppointmentRegisterDTO;
import com.presto.Presto.Med.domain.appointment.AppointmentResponseDTO;
import com.presto.Presto.Med.domain.doctor.Doctor;
import com.presto.Presto.Med.domain.user.User;
import com.presto.Presto.Med.infra.exceptions.InvalidScheduleException;
import com.presto.Presto.Med.repositories.AppointmentRepository;
import com.presto.Presto.Med.repositories.DoctorRepository;
import com.presto.Presto.Med.repositories.UserRepository;
import com.presto.Presto.Med.services.appointment.validations.RegisterValidations;
import com.presto.Presto.Med.services.doctor.DoctorService;
import com.presto.Presto.Med.services.user.UserService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class AppointmentService {

    private final AppointmentRepository appointmentRepository;

    private final DoctorService doctorService;

    private final UserService userService;

    private final List<RegisterValidations> validations;

    @Autowired
    public AppointmentService(List<RegisterValidations> list,
                              UserService userR,
                              DoctorService doctorR,
                              AppointmentRepository appointmentR){
        this.validations = list;
        this.doctorService = doctorR;
        this.userService = userR;
        this.appointmentRepository = appointmentR;
    }

    public Appointment register(AppointmentRegisterDTO dto){
        Doctor doctor = doctorService.findById(dto.getDoctorId());

        User user = userService.findById(dto.getUserId());

        validations.forEach(v -> v.validate(dto));

        return appointmentRepository.save(new Appointment(null, doctor, user, dto.getDate()));
    }

    public Appointment findById(Long id){
        return appointmentRepository
                .findById(id)
                .orElseThrow(() -> new EntityNotFoundException("appointment not found."));
    }

    @Transactional
    public void cancel(Long id){
        if(!appointmentRepository.existsById(id)){
            throw new EntityNotFoundException("appointment not found.");
        }
        appointmentRepository.deleteById(id);
    }
}
