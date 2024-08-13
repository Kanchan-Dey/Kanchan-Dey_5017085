package com.employeemanagementsystem.dto;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class DepartmentDTO {
    private long id;
    private String name;
    private int employeeCount;
    private List<EmployeeDTO> employees;
}
