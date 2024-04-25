package com.presto.Presto.Med.controllers;

import com.presto.Presto.Med.infra.security.TokenDTO;
import com.presto.Presto.Med.domain.user.User;
import com.presto.Presto.Med.domain.user.UserLoginDTO;
import com.presto.Presto.Med.domain.user.UserRegisterDTO;
import com.presto.Presto.Med.domain.user.UserResponseDTO;
import com.presto.Presto.Med.infra.security.TokenService;
import com.presto.Presto.Med.services.auth.AuthService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private AuthService service;

    @Autowired
    private AuthenticationManager manager;

    @Autowired
    private TokenService tokenService;

    @PostMapping("/register")
    public ResponseEntity<UserResponseDTO> register(@RequestBody @Valid UserRegisterDTO dto, UriComponentsBuilder uriBuilder){
        User createdUser = this.service.register(dto);

        URI uri = uriBuilder.path("/auth/register").buildAndExpand(createdUser.getId()).toUri();
        return ResponseEntity.created(uri).body(new UserResponseDTO(createdUser));
    }

    @PostMapping("/login")
    public ResponseEntity<TokenDTO> login(@RequestBody @Valid UserLoginDTO dto){
        var credentials = new UsernamePasswordAuthenticationToken(dto.getEmail(), dto.getPassword());
        var authentication = manager.authenticate(credentials);

        String token = tokenService.generateToken((User) authentication.getPrincipal());
        return ResponseEntity.ok(new TokenDTO(token));
    }
}
