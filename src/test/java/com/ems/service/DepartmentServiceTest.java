package com.ems.service;

import com.ems.model.Department;
import com.ems.repository.DepartmentRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class DepartmentServiceTest {

    @Mock
    private DepartmentRepository departmentRepository;

    @InjectMocks
    private DepartmentService departmentService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetAllDepartments() {
        List<Department> departments = List.of(
                new Department() {{
                    setDepartmentId(1L);
                    setDepartmentName("HR");
                }},
                new Department() {{
                    setDepartmentId(2L);
                    setDepartmentName("IT");
                }}
        );

        when(departmentRepository.findAll()).thenReturn(departments);

        List<Department> result = departmentService.getAllDepartments();
        assertEquals(2, result.size());
        verify(departmentRepository, times(1)).findAll();
    }

    @Test
    void testGetDepartmentByName() {
        Department dept = new Department();
        dept.setDepartmentId(1L);
        dept.setDepartmentName("Finance");

        when(departmentRepository.findByDepartmentName("Finance")).thenReturn(dept);

        Department result = departmentService.getDepartmentByName("Finance");
        assertNotNull(result);
        assertEquals("Finance", result.getDepartmentName());
    }

    @Test
    void testAddDepartment() {
        Department dept = new Department();
        dept.setDepartmentName("Marketing");

        when(departmentRepository.save(dept)).thenReturn(dept);

        Department result = departmentService.addDepartment(dept);
        assertNotNull(result);
        assertEquals("Marketing", result.getDepartmentName());
        verify(departmentRepository).save(dept);
    }
}
