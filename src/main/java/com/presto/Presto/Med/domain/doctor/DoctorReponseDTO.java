package com.presto.Presto.Med.domain.doctor;

import com.presto.Presto.Med.domain.appointment.Appointment;
import com.presto.Presto.Med.enums.DoctorSpecialties;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Setter
public class DoctorReponseDTO {

    private Long id;

    private String name;
    private String last_name;

    private String crm;

    private DoctorSpecialties specialty;

    private Boolean active;

    private List<Appointment> appointments;


    public DoctorReponseDTO(Doctor doctor) {
        this.id = doctor.getId();
        this.name = doctor.getName();
        this.last_name = doctor.getLast_name();
        this.crm = doctor.getCrm();
        this.specialty = doctor.getSpecialty();
        this.active = doctor.getActive();
        this.appointments = doctor.getAppointments();
    }
}
