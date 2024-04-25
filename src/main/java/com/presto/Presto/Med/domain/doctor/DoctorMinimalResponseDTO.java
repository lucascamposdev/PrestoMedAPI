package com.presto.Presto.Med.domain.doctor;

import com.presto.Presto.Med.enums.DoctorSpecialties;
import lombok.Getter;

@Getter
public class DoctorMinimalResponseDTO {
    private Long id;

    private String name;
    private String last_name;

    private String crm;

    private DoctorSpecialties specialty;

    private Boolean active;

    public DoctorMinimalResponseDTO(Doctor doctor) {
        this.id = doctor.getId();
        this.name = doctor.getName();
        this.last_name = doctor.getLast_name();
        this.crm = doctor.getCrm();
        this.specialty = doctor.getSpecialty();
        this.active = doctor.getActive();
    }

}
