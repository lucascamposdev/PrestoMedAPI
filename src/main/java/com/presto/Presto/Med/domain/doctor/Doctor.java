package com.presto.Presto.Med.domain.doctor;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.presto.Presto.Med.domain.appointment.Appointment;
import com.presto.Presto.Med.enums.DoctorSpecialties;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Table(name = "doctors")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@EqualsAndHashCode(of = "id")
public class Doctor {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String last_name;

    private String crm;

    @Enumerated(EnumType.STRING)
    private DoctorSpecialties specialty;

    private Boolean active;

    @OneToMany(mappedBy = "doctor", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Appointment> appointments;

    public Doctor(DoctorRegisterDTO dto) {
        this.name = dto.getName();
        this.last_name = dto.getLast_name();
        this.crm = dto.getCrm();
        this.specialty = dto.getSpecialty();
        this.active = true;
    }
}
