package com.presto.Presto.Med.enums;

import lombok.Getter;

@Getter
public enum DoctorSpecialties {

    CARDIOLOGY("Cardiology"),
    ORTHOPEDICS("Orthopedics"),
    GYNECOLOGY("Gynecology"),
    PEDIATRICS("Pediatrics"),
    DERMATOLOGY("Dermatology");

    private String specialty;

     DoctorSpecialties(String s){
        this.specialty = s;
    }

}
