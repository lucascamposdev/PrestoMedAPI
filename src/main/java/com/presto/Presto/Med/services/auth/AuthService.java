package com.presto.Presto.Med.services.auth;

import com.presto.Presto.Med.domain.user.User;
import com.presto.Presto.Med.domain.user.UserLoginDTO;
import com.presto.Presto.Med.domain.user.UserRegisterDTO;
import com.presto.Presto.Med.infra.exceptions.DataAlreadyExists;
import com.presto.Presto.Med.infra.security.TokenService;
import com.presto.Presto.Med.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService implements UserDetailsService {
    @Autowired
    UserRepository repository;


    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        return this.repository.findByEmail(email);
    }

    public User register(UserRegisterDTO dto){
        if(this.repository.findByEmail(dto.getEmail()) != null){
            throw new DataAlreadyExists("O email já está em uso");
        }

        dto.setPassword(new BCryptPasswordEncoder().encode(dto.getPassword()));

        return this.repository.save(new User(dto));
    }


}