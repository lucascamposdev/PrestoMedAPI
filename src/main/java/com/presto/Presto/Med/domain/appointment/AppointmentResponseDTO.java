package com.presto.Presto.Med.domain.appointment;

import com.presto.Presto.Med.domain.doctor.Doctor;
import com.presto.Presto.Med.domain.user.User;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
public class AppointmentResponseDTO {

    private Long id;

    private Long doctor;

    private Long user;

    private LocalDateTime date;

    public AppointmentResponseDTO(Appointment a){
        this.id = a.getId();
        this.doctor = a.getDoctor().getId();
        this.user = a.getUser().getId();
        this.date = a.getDate();
    }
}
