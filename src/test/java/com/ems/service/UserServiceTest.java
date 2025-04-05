package com.ems.service;

import com.ems.model.User;
import com.ems.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserService userService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetUserByUsername_UserExists() {
        User mockUser = new User();
        mockUser.setUsername("john_doe");

        when(userRepository.findByUsername("john_doe")).thenReturn(Optional.of(mockUser));

        Optional<User> result = userService.getUserByUsername("john_doe");

        assertTrue(result.isPresent());
        assertEquals("john_doe", result.get().getUsername());
    }

    @Test
    void testGetUserByUsername_UserNotFound() {
        when(userRepository.findByUsername("unknown")).thenReturn(Optional.empty());

        Optional<User> result = userService.getUserByUsername("unknown");

        assertFalse(result.isPresent());
    }

    @Test
    void testAddUser() {
        User user = new User();
        user.setUsername("new_user");

        when(userRepository.save(user)).thenReturn(user);

        User saved = userService.addUser(user);

        assertNotNull(saved);
        assertEquals("new_user", saved.getUsername());
    }
}
