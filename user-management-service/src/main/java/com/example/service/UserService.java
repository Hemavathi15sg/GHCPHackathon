package com.example.service;

import com.example.dto.RegisterRequest;
import com.example.dto.RegisterResponse;
import com.example.model.User;
import com.example.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.UUID;

/**
 * User registration service.
 *
 * NOTE (main branch): This is a baseline implementation.
 * Passwords are stored as plain text — this is a known gap to be addressed in USER-142.
 */
@Service
public class UserService {

    private static final Logger log = LoggerFactory.getLogger(UserService.class);

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public RegisterResponse register(RegisterRequest request) {
        User user = new User();
        user.setId(UUID.randomUUID().toString());
        user.setEmail(request.getEmail());
        user.setPassword(request.getPassword()); // TODO USER-142: hash with BCrypt before storing

        userRepository.save(user);

        log.info("User registered successfully: email={}", request.getEmail());
        return new RegisterResponse(user.getId());
    }
}
