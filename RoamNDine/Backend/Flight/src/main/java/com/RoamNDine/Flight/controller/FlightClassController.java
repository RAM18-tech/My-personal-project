package com.RoamNDine.Flight.controller;


import com.RoamNDine.Flight.dto.FlightClassDto;
import com.RoamNDine.Flight.service.FlightClassService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/flight-class")
@RequiredArgsConstructor
public class FlightClassController {

    private final FlightClassService classService;

    @GetMapping
    public List<FlightClassDto> getAll() {
        return classService.getAllClasses();
    }

    @GetMapping("/flight/{flightId}")
    public List<FlightClassDto> getByFlight(@PathVariable int flightId) {
        return classService.getClassesByFlight(flightId);
    }

    @GetMapping("/{id}")
    public FlightClassDto getClassById(@PathVariable int id) {
        return classService.getClassById(id);
    }

    @PostMapping("/add/{flightId}")
    public String addClass(@PathVariable int flightId, @RequestBody FlightClassDto dto) {
        classService.addClass(flightId, dto);
        return "Class added successfully!";
    }

    @PutMapping("/{id}")
    public FlightClassDto update(@PathVariable int id, @RequestBody FlightClassDto dto) {
        return classService.updateClass(id, dto);
    }

    @DeleteMapping("/{id}")
    public String delete(@PathVariable int id) {
        classService.deleteClass(id);
        return "Class deleted successfully!";
    }
}

