package com.ems.repository;

public interface DepartmentStats {
    String getDepartmentName();
    Long getEmployeeCount();
    Double getAverageExperience();  // in years
    Double getTotalGrossSalary();
}
