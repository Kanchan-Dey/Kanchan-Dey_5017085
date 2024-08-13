package com.employeemanagementsystem.repo;

import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import com.employeemanagementsystem.dto.EmployeeDTO;
import com.employeemanagementsystem.entity.Employee;
import com.employeemanagementsystem.projection.EmployeeProjection;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee,Long>{

    List<Employee> findByDepartmentId(Long departmentID);
    
    // Custom Query Methods
    @Query(name="Employee.findByEmail")
    List<EmployeeProjection> findByEmail(@Param("email") String email);
    @Query(name="Employee.findByName")
    List<EmployeeProjection> findByName(@Param("name") String name);

    // Projection using DTO Class
    // @Query("SELECT new com.employeemanagementsystem.dto.EmployeeDTO(e.id,e.name,e.email,e.department.name) FROM Employee e")
    // List<EmployeeDTO> findAllEmployees();
    // Projection using Interface
    @Query("SELECT e FROM Employee e WHERE e.id=:id")
    EmployeeProjection findEmpById(Long id);

    // Using Pageable
    @SuppressWarnings("null")
    @Query("SELECT new com.employeemanagementsystem.dto.EmployeeDTO(e.id,e.name,e.email,e.department.name) FROM Employee e")
    Page<EmployeeDTO> findAll(Pageable pageable);
}
