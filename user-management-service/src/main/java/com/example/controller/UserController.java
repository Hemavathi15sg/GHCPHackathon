package com.example.controller;

import com.example.dto.RegisterRequest;
import com.example.dto.RegisterResponse;
import com.example.exception.UserAlreadyExistsException;
import com.example.exception.UserRegistrationException;
import com.example.service.UserService;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

/**
 * User control ler for handling user registration requests.
 * 
 * - Validates input using @Valid annotation
 * - Handles exceptions with appropriate HTTP status codes
 * - Logs all registration attempts
 */
@RestController
@RequestMapping("/api/users")
public class UserController {

    private static final Logger log = LoggerFactory.getLogger(UserController.class);

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    /**
     * Register a new user.
     * 
     * @param request the registration request (validated)
     * @return 201 CREATED with the user ID
     */
    @PostMapping("/register")
    public ResponseEntity<RegisterResponse> register(@Valid @RequestBody RegisterRequest request) {
        log.info("Registration request received for email={}", request.getEmail());
        RegisterResponse response = userService.register(request);
        log.info("Registration successful: userId={}", response.getUserId());
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    /**
     * Handle UserAlreadyExistsException.
     */
    @ExceptionHandler(UserAlreadyExistsException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    public ResponseEntity<ErrorResponse> handleUserAlreadyExists(UserAlreadyExistsException e) {
        log.warn("User already exists: {}", e.getMessage());
        return ResponseEntity.status(HttpStatus.CONFLICT)
                .body(new ErrorResponse("CONFLICT", e.getMessage()));
    }

    /**
     * Handle UserRegistrationException.
     */
    @ExceptionHandler(UserRegistrationException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<ErrorResponse> handleUserRegistrationError(UserRegistrationException e) {
        log.error("User registration error: {}", e.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(new ErrorResponse("BAD_REQUEST", e.getMessage()));
    }

    /**
     * Handle validation errors.
     */
    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ResponseEntity<ErrorResponse> handleGenericError(Exception e) {
        log.error("Unexpected error during registration", e);
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(new ErrorResponse("INTERNAL_SERVER_ERROR", "An unexpected error occurred"));
    }

    /**
     * Simple error response DTO.
     */
    public static class ErrorResponse {
        private String code;
        private String message;

        public ErrorResponse(String code, String message) {
            this.code = code;
            this.message = message;
        }

        public String getCode() { return code; }
        public String getMessage() { return message; }
    }
}
