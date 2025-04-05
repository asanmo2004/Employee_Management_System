package com.ems.controller;

import com.ems.repository.DepartmentStats;
import com.ems.repository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/stats")
public class StatsController {

    @Autowired
    private EmployeeRepository employeeRepository;

    @GetMapping("/departments")
    public List<DepartmentStats> getDepartmentStats() {
        return employeeRepository.getDepartmentStats();
    }
}
