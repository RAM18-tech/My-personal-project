package com.cts.flightBookingService.controller;

import com.cts.flightBookingService.dto.ClassTypeDto;
import com.cts.flightBookingService.service.ClassTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/admin/class-types")
public class ClassTypeController {

    @Autowired
    private ClassTypeService classTypeService;

    @GetMapping
    public ResponseEntity<List<ClassTypeDto>> getAllClassTypes() {
        return ResponseEntity.ok(classTypeService.getAllClassTypes());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ClassTypeDto> getClassTypeById(@PathVariable int id) {
        return ResponseEntity.ok(classTypeService.getClassTypeById(id));
    }

    @PostMapping
    public ResponseEntity<ClassTypeDto> addClassType(@RequestBody ClassTypeDto dto) {
        return new ResponseEntity<>(classTypeService.addClassType(dto), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ClassTypeDto> updateClassType(@PathVariable int id, @RequestBody ClassTypeDto dto) {
        return new ResponseEntity<>(classTypeService.updateClassType(id, dto), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteClassType(@PathVariable int id) {
        classTypeService.deleteClassType(id);
        return ResponseEntity.ok("ClassType deleted with ID: " + id);
    }

}