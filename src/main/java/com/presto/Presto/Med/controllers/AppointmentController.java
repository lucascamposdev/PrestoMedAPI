package com.presto.Presto.Med.controllers;

import com.presto.Presto.Med.domain.appointment.Appointment;
import com.presto.Presto.Med.domain.appointment.AppointmentRegisterDTO;
import com.presto.Presto.Med.domain.appointment.AppointmentResponseDTO;
import com.presto.Presto.Med.domain.appointment.AppointmentUpdateDTO;
import com.presto.Presto.Med.services.appointment.AppointmentService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;

@RestController
@RequestMapping("appointments")
@SecurityRequirement(name = "bearer-key")
public class AppointmentController {

    @Autowired
    private AppointmentService service;

    @PostMapping
    public ResponseEntity<AppointmentResponseDTO> register(@RequestBody @Valid AppointmentRegisterDTO dto, UriComponentsBuilder uriBuilder){

        Appointment createdAppointment = service.register(dto);

        URI uri = uriBuilder.path("/apppointments").buildAndExpand(createdAppointment.getId()).toUri();
        return ResponseEntity.created(uri).body(new AppointmentResponseDTO(createdAppointment));
    }

    @GetMapping("/{id}")
    public ResponseEntity<AppointmentResponseDTO> findById(@PathVariable Long id){
        Appointment appointment = service.findById(id);
        return ResponseEntity.ok(new AppointmentResponseDTO(appointment));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> cancel(@PathVariable Long id){
        service.cancel(id);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/{id}")
    public ResponseEntity<?> reschedule(@RequestBody AppointmentUpdateDTO dto, @PathVariable Long id){
        Appointment appointment = service.reschedule(id, dto);
        return ResponseEntity.ok(new AppointmentResponseDTO(appointment));
    }
}
