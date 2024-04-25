package com.presto.Presto.Med.domain.user;

import com.presto.Presto.Med.domain.appointment.Appointment;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@NoArgsConstructor
@Getter
public class UserResponseDTO {
    private Long id;
    private String name;
    private String last_name;
    private String email;
    private String phone;
    private List<Appointment> appointments;

    public UserResponseDTO(User user){
        this.id = user.getId();
        this.name = user.getName();
        this.last_name = user.getLast_name();
        this.email = user.getEmail();
        this.phone = user.getPhone();
        this.appointments = user.getAppointments();
    }
}
