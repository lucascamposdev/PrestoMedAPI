package com.presto.Presto.Med.controllers;

import com.presto.Presto.Med.domain.doctor.Doctor;
import com.presto.Presto.Med.domain.doctor.DoctorMinimalResponseDTO;
import com.presto.Presto.Med.domain.doctor.DoctorRegisterDTO;
import com.presto.Presto.Med.domain.doctor.DoctorReponseDTO;
import com.presto.Presto.Med.services.doctor.DoctorService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;


@RestController
@RequestMapping("/doctors")
@SecurityRequirement(name = "bearer-key")
public class DoctorController {

    private final DoctorService service;

    public DoctorController(DoctorService service){
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<DoctorMinimalResponseDTO> register(@RequestBody @Valid DoctorRegisterDTO dto, UriComponentsBuilder uriBuilder){
        Doctor createdDoctor = service.create(dto);

        URI uri = uriBuilder.path("/doctors").buildAndExpand(createdDoctor.getId()).toUri();
        return ResponseEntity.created(uri).body(new DoctorMinimalResponseDTO(createdDoctor));
    }

    @GetMapping
    public ResponseEntity<Page<DoctorMinimalResponseDTO>> getAll(Pageable page){
        Page<DoctorMinimalResponseDTO> listOfDoctors = service
                .listAllDoctors(page)
                .map(DoctorMinimalResponseDTO::new);

        return ResponseEntity.ok(listOfDoctors);
    }

    @GetMapping("/specialty/{specialty}")
    public ResponseEntity<Page<DoctorMinimalResponseDTO>> findBySpecialty(
            @PathVariable String specialty,
            Pageable page){

        Page<DoctorMinimalResponseDTO> listOfDoctors = service
                .findBySpecialty(specialty, page)
                .map(DoctorMinimalResponseDTO::new);

        return ResponseEntity.ok(listOfDoctors);
    }

    @GetMapping("/{id}")
    public ResponseEntity<DoctorReponseDTO> findById(@PathVariable Long id){
        Doctor foundDoctor = service.findById(id);
        return ResponseEntity.ok(new DoctorReponseDTO(foundDoctor));
    }

    @PatchMapping("/{id}")
    public ResponseEntity<DoctorMinimalResponseDTO> changeActiveStatus(@PathVariable Long id){
        Doctor updatedDoctor = service.changeActiveStatus(id);
        return ResponseEntity.ok(new DoctorMinimalResponseDTO(updatedDoctor));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id){
        service.deleteDoctor(id);
        return ResponseEntity.noContent().build();
    }
}
