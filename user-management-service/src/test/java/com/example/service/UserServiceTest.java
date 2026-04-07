package com.example.service;

import com.example.dto.RegisterRequest;
import com.example.dto.RegisterResponse;
import com.example.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * Unit tests for UserService.
 *
 * Coverage status (intentionally incomplete to demonstrate JaCoCo/PITest findings):
 *   - Happy path: covered
 *   - Duplicate email: NOT tested (will be a gap after PR adds duplicate check)
 *   - Password validation: NOT tested (will be a gap after PR adds length check)
 *   - Password hashing: NOT tested (will be a gap after PR adds BCrypt)
 */
@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    private UserService userService;

    @BeforeEach
    void setUp() {
        userService = new UserService(userRepository);
    }

    @Test
    void register_validRequest_savesUserAndReturnsId() {
        RegisterRequest request = new RegisterRequest("test@example.com", "password123");

        RegisterResponse response = userService.register(request);

        assertThat(response.getUserId()).isNotNull();
        verify(userRepository).save(any());
    }

    // TODO: Add after USER-142 PR is merged:
    // @Test void register_duplicateEmail_throwsDuplicateEmailException()
    // @Test void register_passwordTooShort_throwsIllegalArgumentException()
    // @Test void register_validPassword_isStoredAsHash()
    // @Test void register_eightCharPassword_isAccepted()     // boundary test
    // @Test void register_sevenCharPassword_isRejected()     // boundary test
}
