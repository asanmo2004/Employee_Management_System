package com.ems.controller;

import com.ems.model.User;
import com.ems.service.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

class UserControllerTest {

    private MockMvc mockMvc;

    @Mock
    private UserService userService;

    @InjectMocks
    private UserController userController;

    private final ObjectMapper objectMapper = new ObjectMapper();

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(userController).build();
    }

    @Test
    void testGetUserByUsername_Found() throws Exception {
        User user = new User();
        user.setUsername("john");
        user.setPasswordHash("password123"); // optional for the test

        when(userService.getUserByUsername("john")).thenReturn(Optional.of(user));

        mockMvc.perform(get("/users/john"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.username").value("john"));
    }

    @Test
    void testGetUserByUsername_NotFound() throws Exception {
        when(userService.getUserByUsername("notfound")).thenReturn(Optional.empty());

        mockMvc.perform(get("/users/notfound"))
                .andExpect(status().isNotFound());
    }

    @Test
    void testAddUser() throws Exception {
        User user = new User();
        user.setUsername("alice");
        user.setPasswordHash("securepass");

        when(userService.addUser(any(User.class))).thenReturn(user);

        mockMvc.perform(post("/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(user)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.username").value("alice"));
    }
}
