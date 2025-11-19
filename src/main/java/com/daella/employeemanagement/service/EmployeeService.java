package com.daella.employeemanagement.service;


import com.daella.employeemanagement.dto.EmployeeRequest;
import com.daella.employeemanagement.dto.EmployeeResponse;
import com.daella.employeemanagement.model.Employee;
import com.daella.employeemanagement.model.User;
import com.daella.employeemanagement.repository.EmployeeRepository;
import com.daella.employeemanagement.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
@RequiredArgsConstructor
public class EmployeeService {

    private final EmployeeRepository employeeRepository;
    private final UserRepository userRepository;


    @Transactional
    public EmployeeResponse createEmployee(EmployeeRequest request, String creatorUsername) {
        User creator = userRepository.findByUsername(creatorUsername)
                .orElseThrow(() -> new UsernameNotFoundException("Creator not found"));
        Employee employee = Employee.builder()
                .name(request.getName())
                .position(request.getPosition())
                .department(request.getDepartment())
                .hireDate(request.getHireDate())
                .createdBy(creator)
                .build();
        Employee saved = employeeRepository.save(employee);
        return toEmployeeResponse(saved);
    }


    @Transactional(readOnly = true)
    public Page<EmployeeResponse> getEmployees(Pageable pageable) {
        return employeeRepository.findAll(pageable).map(this::toEmployeeResponse);
    }


    @Transactional(readOnly = true)
    public EmployeeResponse getEmployeeById(Long id) {
        Employee employee = employeeRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Employee not found"));
        return toEmployeeResponse(employee);
    }


    @Transactional
    public EmployeeResponse updateEmployee(Long id, EmployeeRequest request) {
        Employee employee = employeeRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Employee not found"));
        employee.setName(request.getName());
        employee.setPosition(request.getPosition());
        employee.setDepartment(request.getDepartment());
        employee.setHireDate(request.getHireDate());
        Employee saved = employeeRepository.save(employee);
        return toEmployeeResponse(saved);
    }


    @Transactional
    public void deleteEmployee(Long id) {
        if (!employeeRepository.existsById(id)) {
            throw new IllegalArgumentException("Employee not found");
        }
        employeeRepository.deleteById(id);
    }


    private EmployeeResponse toEmployeeResponse(Employee employee) {
        return EmployeeResponse.builder()
                .id(employee.getId())
                .name(employee.getName())
                .position(employee.getPosition())
                .department(employee.getDepartment())
                .hireDate(employee.getHireDate())
                .build();
    }
}