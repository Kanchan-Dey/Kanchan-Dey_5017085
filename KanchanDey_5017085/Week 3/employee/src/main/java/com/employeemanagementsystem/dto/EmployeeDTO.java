package com.employeemanagementsystem.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class EmployeeDTO {
    private long id;
    private String name;
    private String email;
    private String departmentName;
}
