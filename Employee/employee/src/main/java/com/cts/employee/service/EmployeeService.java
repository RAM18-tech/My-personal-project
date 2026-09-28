package com.cts.employee.service;


import com.cts.employee.dto.EmployeeDto;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface EmployeeService {


    List<EmployeeDto> getAllEmployee();

    EmployeeDto getEmployeeById(int id);

    void addEmployee(EmployeeDto dto);

    void deleteEmployee(int id);
}
