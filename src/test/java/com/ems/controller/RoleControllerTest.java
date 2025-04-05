package com.ems.controller;

import com.ems.model.Role;
import com.ems.service.RoleService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Arrays;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

class RoleControllerTest {

    private MockMvc mockMvc;

    @Mock
    private RoleService roleService;

    @InjectMocks
    private RoleController roleController;

    private final ObjectMapper objectMapper = new ObjectMapper();

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(roleController).build();
    }

    @Test
    void testGetAllRoles() throws Exception {
        Role admin = new Role();
        admin.setRoleId(1L);
        admin.setRoleName("ADMIN");

        when(roleService.getAllRoles()).thenReturn(Arrays.asList(admin));

        mockMvc.perform(get("/roles"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].roleName").value("ADMIN"));
    }

    @Test
    void testGetRoleByName_Found() throws Exception {
        Role role = new Role();
        role.setRoleName("EMPLOYEE");

        when(roleService.getRoleByName("EMPLOYEE")).thenReturn(role);

        mockMvc.perform(get("/roles/EMPLOYEE"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.roleName").value("EMPLOYEE"));
    }

    @Test
    void testGetRoleByName_NotFound() throws Exception {
        when(roleService.getRoleByName("UNKNOWN")).thenReturn(null);

        mockMvc.perform(get("/roles/UNKNOWN"))
                .andExpect(status().isNotFound());
    }

    @Test
    void testAddRole() throws Exception {
        Role role = new Role();
        role.setRoleName("MANAGER");

        when(roleService.addRole(any(Role.class))).thenReturn(role);

        mockMvc.perform(post("/roles")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(role)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.roleName").value("MANAGER"));
    }
}
