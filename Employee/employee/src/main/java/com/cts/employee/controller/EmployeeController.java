package com.cts.employee.controller;


import com.cts.employee.dto.EmployeeDto;
import com.cts.employee.entity.Employee;
import com.cts.employee.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Scanner;

@RestController
@RequestMapping("/employee")
public class EmployeeController {

    @Autowired
    private EmployeeService employeeService;

    @GetMapping("/all")
    private ResponseEntity<List<EmployeeDto>> getAllEmployee(){
        List<EmployeeDto> employee= employeeService.getAllEmployee();
        return new ResponseEntity<>(employee, HttpStatus.OK);

    }

    @GetMapping("/{id}")
    private ResponseEntity<EmployeeDto> getEmployeeById(@PathVariable int id){
        EmployeeDto emp=employeeService.getEmployeeById(id);
        return new ResponseEntity<>(emp,HttpStatus.OK);

    }

    @PostMapping("/add")
    private ResponseEntity<String> addEmployee(@RequestBody EmployeeDto dto){
        employeeService.addEmployee(dto);
        return new ResponseEntity<>("employee added",HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    private ResponseEntity<String> deleteEmployee(@PathVariable int id){
        employeeService.deleteEmployee(id);
        return new ResponseEntity<>("employee deleted",HttpStatus.OK);
    }




}
