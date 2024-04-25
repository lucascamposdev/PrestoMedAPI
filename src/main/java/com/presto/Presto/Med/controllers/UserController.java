package com.presto.Presto.Med.controllers;

import com.presto.Presto.Med.domain.user.User;
import com.presto.Presto.Med.domain.user.UserResponseDTO;
import com.presto.Presto.Med.services.user.UserService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/users")
@SecurityRequirement(name = "bearer-key")
public class UserController {

    @Autowired
    private UserService service;

    @GetMapping("/{id}")
    public ResponseEntity<UserResponseDTO> getUser(@PathVariable Long id){
        User foundUser = service.findByIdWithAppointments(id);
        return ResponseEntity.ok(new UserResponseDTO(foundUser));
    }
}
