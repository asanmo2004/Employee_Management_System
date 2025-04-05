package com.ems.service;

import com.ems.model.Role;
import com.ems.repository.RoleRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class RoleServiceTest {

    @Mock
    private RoleRepository roleRepository;

    @InjectMocks
    private RoleService roleService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetAllRoles() {
        Role admin = new Role();
        admin.setRoleName("ADMIN");

        Role employee = new Role();
        employee.setRoleName("EMPLOYEE");

        List<Role> roleList = Arrays.asList(admin, employee);
        when(roleRepository.findAll()).thenReturn(roleList);

        List<Role> result = roleService.getAllRoles();

        assertEquals(2, result.size());
        assertEquals("ADMIN", result.get(0).getRoleName());
    }

    @Test
    void testGetRoleByName() {
        Role manager = new Role();
        manager.setRoleName("MANAGER");

        when(roleRepository.findByRoleName("MANAGER")).thenReturn(manager);

        Role result = roleService.getRoleByName("MANAGER");

        assertNotNull(result);
        assertEquals("MANAGER", result.getRoleName());
    }

    @Test
    void testAddRole() {
        Role newRole = new Role();
        newRole.setRoleName("INTERN");

        when(roleRepository.save(newRole)).thenReturn(newRole);

        Role saved = roleService.addRole(newRole);

        assertNotNull(saved);
        assertEquals("INTERN", saved.getRoleName());
    }
}
