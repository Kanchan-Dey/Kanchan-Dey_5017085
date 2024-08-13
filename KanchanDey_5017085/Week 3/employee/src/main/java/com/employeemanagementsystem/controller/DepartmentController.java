package com.employeemanagementsystem.controller;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.employeemanagementsystem.dto.DepartmentDTO;
import com.employeemanagementsystem.entity.Department;
import com.employeemanagementsystem.projection.DepartmentProjection;
import com.employeemanagementsystem.service.DepartmentService;

@RestController
@RequestMapping("/api/departments")
public class DepartmentController {
    @Autowired
    private DepartmentService departmentService;
    // Start of DTO and Projections controlled by GetMapping
    @GetMapping
    public List<DepartmentProjection> getAllDepartments(){
        return departmentService.getAllDepartments();
    }
    @GetMapping("/{id}")
    public DepartmentProjection getDeptById(@PathVariable Long id){
        return departmentService.getDeptById(id);
    }
    @GetMapping("/name/{name}")
    public DepartmentDTO getDepartmentByName(@PathVariable String name){
        return departmentService.getDepartmentByName(name);
    }
    // End of DTO and Projections controlled by GetMapping
    
    public Department getDepartmentById(@PathVariable Long id){
        return departmentService.getDepartmentById(id);
    }
    @PostMapping
    public Department createDepartment(@RequestBody Department department){
        return departmentService.saveDepartment(department);
    }
    @PutMapping("/{id}")
    public Department updateDepartment(@PathVariable Long id, @RequestBody Department departmentDetails){
        Department department=departmentService.getDepartmentById(id);
        if(department!=null){
            department.setEmployees(departmentDetails.getEmployees());
            department.setName(departmentDetails.getName());
            return departmentService.saveDepartment(department);
        }
        return null;
    }
    @DeleteMapping("/{id}")
    public void deleteDepartment(@PathVariable Long id){
        departmentService.deleteDepartment(id);
    }
}
