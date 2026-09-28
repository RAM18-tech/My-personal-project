package com.cts.flightBookingService.controller;

import com.cts.flightBookingService.dto.FlightDto;
import com.cts.flightBookingService.dto.FlightResponseDto;
import com.cts.flightBookingService.service.FlightService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/admin/flights")
public class FlightController {

    @Autowired
    private FlightService flightService;

    @PostMapping
    public ResponseEntity<FlightResponseDto> addFlight(@RequestBody FlightDto flightDto) {
        FlightResponseDto flight = flightService.addFlight(flightDto);
        return new ResponseEntity<>(flight, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<FlightResponseDto> updateFlight(@PathVariable int id, @RequestBody FlightDto flightDto) {
        FlightResponseDto flight = flightService.updateFlight(id, flightDto);
        return new ResponseEntity<>(flight, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteFlight(@PathVariable int id) {
        flightService.deleteFlight(id);
        return ResponseEntity.ok("Flight deleted with ID: " + id);
    }

    @GetMapping
    public ResponseEntity<List<FlightResponseDto>> getAllFlights() {
        List<FlightResponseDto> flights = flightService.getAllFlights();
        return new ResponseEntity<>(flights, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<FlightResponseDto> getFlightById(@PathVariable int id) {
        FlightResponseDto flight = flightService.getFlightById(id);
        return ResponseEntity.ok(flight);
    }
}
