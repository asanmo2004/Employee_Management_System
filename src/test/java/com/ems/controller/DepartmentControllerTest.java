package com.ems.controller;

import com.ems.model.Department;
import com.ems.service.DepartmentService;
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

import static org.mockito.Mockito.when;
import static org.mockito.Mockito.any;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

class DepartmentControllerTest {

    private MockMvc mockMvc;

    @Mock
    private DepartmentService departmentService;

    @InjectMocks
    private DepartmentController departmentController;

    private ObjectMapper objectMapper;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(departmentController).build();
        objectMapper = new ObjectMapper();
    }

    @Test
    void testGetAllDepartments() throws Exception {
        Department dep = new Department();
        dep.setDepartmentId(1L);
        dep.setDepartmentName("HR");

        when(departmentService.getAllDepartments()).thenReturn(Arrays.asList(dep));

        mockMvc.perform(get("/departments"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].departmentName").value("HR"));
    }

    @Test
    void testGetDepartmentByName() throws Exception {
        Department dep = new Department();
        dep.setDepartmentId(2L);
        dep.setDepartmentName("Engineering");

        when(departmentService.getDepartmentByName("Engineering")).thenReturn(dep);

        mockMvc.perform(get("/departments/Engineering"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.departmentName").value("Engineering"));
    }

    @Test
    void testGetDepartmentByName_NotFound() throws Exception {
        when(departmentService.getDepartmentByName("Unknown")).thenReturn(null);

        mockMvc.perform(get("/departments/Unknown"))
                .andExpect(status().isNotFound());
    }

    @Test
    void testAddDepartment() throws Exception {
        Department dep = new Department();
        dep.setDepartmentName("Finance");

        when(departmentService.addDepartment(any(Department.class))).thenReturn(dep);

        mockMvc.perform(post("/departments")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dep)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.departmentName").value("Finance"));
    }
}
