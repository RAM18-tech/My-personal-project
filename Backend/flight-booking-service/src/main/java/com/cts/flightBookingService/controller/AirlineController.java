package com.cts.flightBookingService.controller;

import com.cts.flightBookingService.dto.AirlineDto;
import com.cts.flightBookingService.service.AirlineService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/admin/airlines")
public class AirlineController {

    @Autowired
    private AirlineService airlineService;

    @PostMapping
    public ResponseEntity<AirlineDto> addAirline(@RequestBody AirlineDto dto) {
        AirlineDto saved = airlineService.addAirline(dto);
        return new ResponseEntity<>(saved, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<AirlineDto> updateAirline(@PathVariable int id, @RequestBody AirlineDto dto) {
        AirlineDto updated = airlineService.updateAirline(id, dto);
        return new ResponseEntity<>(updated, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteAirline(@PathVariable int id) {
        airlineService.deleteAirline(id);
        return ResponseEntity.ok("Airline deleted with ID: " + id);
    }

    @GetMapping
    public ResponseEntity<List<AirlineDto>> getAllAirlines() {
        List<AirlineDto> airlines = airlineService.getAllAirlines();
        return new ResponseEntity<>(airlines, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<AirlineDto> getAirlineById(@PathVariable int id) {
        AirlineDto airline = airlineService.getAirlineById(id);
        return ResponseEntity.ok(airline);
    }
}
