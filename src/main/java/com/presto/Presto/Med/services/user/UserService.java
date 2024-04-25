package com.presto.Presto.Med.services.user;

import com.presto.Presto.Med.domain.user.User;
import com.presto.Presto.Med.repositories.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public User findByIdWithAppointments(Long id){
        return userRepository.findByIdWithAppointments(id)
                .orElseThrow(() -> new EntityNotFoundException("user not found."));
    }

    public User findById(Long id){
        return userRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("user not found."));
    }
}
