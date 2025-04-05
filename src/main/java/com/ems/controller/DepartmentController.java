package com.ems.controller;

import com.ems.model.Department;
import com.ems.service.DepartmentService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/departments")
public class DepartmentController {

    private static final Logger logger = LoggerFactory.getLogger(DepartmentController.class);

    @Autowired
    private DepartmentService departmentService;

    @GetMapping
    public List<Department> getAllDepartments() {
        logger.info("Fetching all departments");
        return departmentService.getAllDepartments();
    }

    @GetMapping("/{name}")
    public ResponseEntity<Department> getDepartmentByName(@PathVariable String name) {
        logger.info("Fetching department by name: {}", name);
        Department department = departmentService.getDepartmentByName(name);
        if (department != null) {
            logger.debug("Department found: {}", department.getDepartmentName());
            return ResponseEntity.ok(department);
        } else {
            logger.warn("Department not found with name: {}", name);
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping
    public Department addDepartment(@RequestBody Department department) {
        logger.info("Adding new department: {}", department.getDepartmentName());
        return departmentService.addDepartment(department);
    }
}
