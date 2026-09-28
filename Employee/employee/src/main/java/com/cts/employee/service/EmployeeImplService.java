package com.cts.employee.service;

import com.cts.employee.dto.EmployeeDto;
import com.cts.employee.entity.Employee;
import com.cts.employee.repo.EmployeeRepo;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.security.PrivateKey;
import java.util.List;
import java.util.stream.Collectors;


@Service
public class EmployeeImplService implements EmployeeService{

    @Autowired
    private EmployeeRepo employeeRepo;

    @Autowired
    private ModelMapper modelMapper;



    @Override
    public List<EmployeeDto> getAllEmployee() {
        List<Employee> employees=employeeRepo.findAll();
        return employees.stream()
                .map(emp -> modelMapper.map(emp,EmployeeDto.class))
                .collect(Collectors.toList());

    }

    @Override
    public EmployeeDto getEmployeeById(int id) {
        return null;
    }


    @Override
    public void addEmployee(EmployeeDto dto) {
        Employee employee = modelMapper.map(dto,Employee.class);
        employeeRepo.save(employee);
    }

    @Override
    public void deleteEmployee(int id) {

    }
}
