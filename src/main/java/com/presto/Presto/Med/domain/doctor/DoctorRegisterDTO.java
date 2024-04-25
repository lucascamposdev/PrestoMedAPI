package com.presto.Presto.Med.domain.doctor;

import com.presto.Presto.Med.enums.DoctorSpecialties;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class DoctorRegisterDTO {

    @NotBlank(message = "name must not be null.")
    private String name;
    @NotBlank(message = "last name must not be null.")
    private String last_name;
    @NotBlank(message = "crm must not be null.")
    private String crm;
    @NotNull(message = "specialty must not be null.")
    private DoctorSpecialties specialty;
}
