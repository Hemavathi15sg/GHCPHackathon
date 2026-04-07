package com.example.service;

import com.example.dto.RegisterRequest;
import com.example.dto.RegisterResponse;
import com.example.exception.UserAlreadyExistsException;
import com.example.exception.UserRegistrationException;
import com.example.model.User;
import com.example.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

/**
 * User registration service with security, validation, and error handling.
 * 
 * SECURITY: Passwords are hashed using BCrypt before storage (strength: 12).
 * VALIDATION: Email and password are validated before processing.
 * LOGGING: All registration attempts are logged with appropriate levels.
 */
@Service
@Transactional
public class UserService {

    private static final Logger log = LoggerFactory.getLogger(UserService.class);
    private static final int BCRYPT_STRENGTH = 12;

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
        this.passwordEncoder = new BCryptPasswordEncoder(BCRYPT_STRENGTH);
    }

    /**
     * Register a new user with password hashing and validation.
     * 
     * @param request the registration request with email and password
     * @return the registration response with user ID
     * @throws UserAlreadyExistsException if email is already registered
     * @throws UserRegistrationException if registration fails
     */
    public RegisterResponse register(RegisterRequest request) {
        try {
            // Validate input
            if (request == null || request.getEmail() == null || request.getPassword() == null) {
                log.warn("Invalid registration request: missing email or password");
                throw new UserRegistrationException("Email and password are required");
            }

            String email = request.getEmail().trim().toLowerCase();

            // Check if user already exists
            Optional<User> existingUser = userRepository.findByEmail(email);
            if (existingUser.isPresent()) {
                log.warn("Registration failed: user already exists with email={}", email);
                throw new UserAlreadyExistsException("User with email " + email + " already exists");
            }

            // Create new user
            User user = new User();
            user.setId(UUID.randomUUID().toString());
            user.setEmail(email);
            // SECURITY: Hash password using BCrypt before storing
            user.setPassword(passwordEncoder.encode(request.getPassword()));
            user.setCreatedAt(LocalDateTime.now());

            // Save user
            User savedUser = userRepository.save(user);

            log.info("User registered successfully: userId={}, email={}", savedUser.getId(), email);
            return new RegisterResponse(savedUser.getId());

        } catch (UserAlreadyExistsException | UserRegistrationException e) {
            throw e;
        } catch (Exception e) {
            log.error("Unexpected error during user registration", e);
            throw new UserRegistrationException("Registration failed: " + e.getMessage(), e);
        }
    }
}
