package com.employeemanagementsystem.service;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.employeemanagementsystem.dto.EmployeeDTO;
import com.employeemanagementsystem.entity.Employee;
import com.employeemanagementsystem.projection.EmployeeProjection;
import com.employeemanagementsystem.repo.EmployeeRepository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import lombok.NonNull;

@Service
public class EmployeeService {
    @Autowired
    private EmployeeRepository employeeRepository;

    @PersistenceContext
    private EntityManager entityManager;
   
    @NonNull
    public Page<EmployeeDTO> getAllEmployees(Pageable pageable){
        return employeeRepository.findAll(pageable);
    }
    // START OF PROJECTIONS
    public EmployeeProjection getEmpById(Long id){
        return employeeRepository.findEmpById(id);
    }
    public List<EmployeeProjection> findByEmail(String email){
        return employeeRepository.findByEmail(email);
    }
    public List<EmployeeProjection> findByName(String name){
        return employeeRepository.findByName(name);
    }
    // END OF PPROJECTIONS

    public Employee getEmployeeById(Long id){
        return employeeRepository.findById(id).orElse(null);
    }
    public void deleteEmployee(Long id){
        employeeRepository.deleteById(id);
    }
    public Employee saveEmployee(Employee employee){
        return employeeRepository.save(employee);
    }
    @Transactional
    public void batchInsert(List<Employee> employees) {
        int batchSize = 30;
        for (int i = 0; i < employees.size(); i++) {
            Employee employee = employees.get(i);
            if (employee.getId() == null) {
                // New entity, so persist
                entityManager.persist(employee);
            } else {
                // Existing entity, so merge
                entityManager.merge(employee);
            }
            if (i % batchSize == 0 && i > 0) {
                // Flush a batch of inserts and release memory
                entityManager.flush();
                entityManager.clear();
            }
        }
        // Flush remaining entities
        entityManager.flush();
        entityManager.clear();
    }

}
