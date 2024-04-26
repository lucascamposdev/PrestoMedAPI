package com.presto.Presto.Med.domain.appointment;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AppointmentUpdateDTO {

    @NotNull(message = "date time must not be null.")
    @Future(message = "time must be in future.")
    @JsonFormat(pattern = "dd-MM-yyyy HH:mm")
    private LocalDateTime date;
}
