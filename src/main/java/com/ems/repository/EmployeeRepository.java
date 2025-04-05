package com.ems.repository;

import com.ems.repository.DepartmentStats;
import com.ems.model.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import java.util.List;
@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Long> {
    // Custom Queries
    @Query("""
        SELECT 
            e.department.departmentName AS departmentName,
            COUNT(e) AS employeeCount,
            AVG(TIMESTAMPDIFF(YEAR, e.hireDate, CURRENT_DATE)) AS averageExperience,
            SUM(e.basicSalary + e.bonus - e.deductions) AS totalGrossSalary
        FROM Employee e
        WHERE e.department IS NOT NULL
        GROUP BY e.department.departmentName
        """)
    List<DepartmentStats> getDepartmentStats();
}
