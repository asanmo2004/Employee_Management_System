package com.ems.service;

import com.ems.model.Employee;
import com.ems.repository.EmployeeRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;

import java.math.BigDecimal;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class EmployeeServiceTest {

    @Mock
    private EmployeeRepository employeeRepository;

    @InjectMocks
    private EmployeeService employeeService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetAllEmployees() {
        List<Employee> employees = List.of(new Employee(), new Employee());

        when(employeeRepository.findAll()).thenReturn(employees);

        List<Employee> result = employeeService.getAllEmployees();
        assertEquals(2, result.size());
        verify(employeeRepository, times(1)).findAll();
    }

    @Test
    void testGetEmployeeById() {
        Employee employee = new Employee();
        employee.setEmployeeId(1L);

        when(employeeRepository.findById(1L)).thenReturn(Optional.of(employee));

        Optional<Employee> result = employeeService.getEmployeeById(1L);
        assertTrue(result.isPresent());
        assertEquals(1L, result.get().getEmployeeId());
    }

    @Test
    void testAddEmployee() {
        Employee employee = new Employee();
        employee.setFirstName("John");

        when(employeeRepository.save(employee)).thenReturn(employee);

        Employee result = employeeService.addEmployee(employee);
        assertNotNull(result);
        assertEquals("John", result.getFirstName());
        verify(employeeRepository).save(employee);
    }

    @Test
    void testUpdateEmployee() {
        Employee existing = new Employee();
        existing.setEmployeeId(1L);
        existing.setFirstName("Old");
        existing.setBasicSalary(BigDecimal.valueOf(1000));
        existing.setBonus(BigDecimal.valueOf(200));
        existing.setDeductions(BigDecimal.valueOf(100));

        Employee updatedDetails = new Employee();
        updatedDetails.setFirstName("New");
        updatedDetails.setLastName("User");
        updatedDetails.setEmail("new@company.com");
        updatedDetails.setPhone("1234567890");
        updatedDetails.setBasicSalary(BigDecimal.valueOf(1500));
        updatedDetails.setBonus(BigDecimal.valueOf(300));
        updatedDetails.setDeductions(BigDecimal.valueOf(200));

        when(employeeRepository.findById(1L)).thenReturn(Optional.of(existing));
        when(employeeRepository.save(any(Employee.class))).thenAnswer(i -> i.getArgument(0));

        Employee result = employeeService.updateEmployee(1L, updatedDetails);
        assertNotNull(result);
        assertEquals("New", result.getFirstName());
        assertEquals(BigDecimal.valueOf(1600), result.getSalary()); // 1500 + 300 - 200
        verify(employeeRepository).save(any(Employee.class));
    }

    @Test
    void testUpdateEmployee_NotFound() {
        Employee updatedDetails = new Employee();
        when(employeeRepository.findById(999L)).thenReturn(Optional.empty());

        Employee result = employeeService.updateEmployee(999L, updatedDetails);
        assertNull(result);
        verify(employeeRepository, never()).save(any());
    }

    @Test
    void testDeleteEmployee() {
        doNothing().when(employeeRepository).deleteById(1L);

        employeeService.deleteEmployee(1L);

        verify(employeeRepository, times(1)).deleteById(1L);
    }
}
