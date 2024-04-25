package com.presto.Presto.Med.domain.appointment;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.presto.Presto.Med.domain.doctor.Doctor;
import com.presto.Presto.Med.domain.user.User;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class AppointmentRegisterDTO {

    @NotNull(message = "doctor id must not be null.")
    private Long doctorId;

    @NotNull(message = "user id must not be null.")
    private Long userId;

    @NotNull(message = "date time must not be null.")
    @Future(message = "time must be in future.")
    @JsonFormat(pattern = "dd-MM-yyyy HH:mm")
    private LocalDateTime date;
}
