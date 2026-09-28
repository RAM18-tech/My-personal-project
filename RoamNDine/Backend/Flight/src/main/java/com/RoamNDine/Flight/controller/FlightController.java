package com.RoamNDine.Flight.controller;


import com.RoamNDine.Flight.dto.FlightDto;
import com.RoamNDine.Flight.service.FlightClassService;
import com.RoamNDine.Flight.service.FlightService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/flights")
@RequiredArgsConstructor
public class FlightController {

    private final FlightService flightService;


    @GetMapping("/all")
    public ResponseEntity<List<FlightDto>> getAllFlights() {
        List<FlightDto> flights= flightService.getAllFlights();
        return new ResponseEntity<>(flights, HttpStatus.OK);
    }

    @PostMapping("/add")
    public ResponseEntity<String> addFlight(@RequestBody FlightDto dto){
        flightService.addFlight(dto);
        return new ResponseEntity<>("Flight Added",HttpStatus.CREATED);
    }

    @GetMapping("{id}")
    public ResponseEntity<FlightDto> getFLightById(@PathVariable() int id){
        FlightDto dto = flightService.getFlightById(id);
        return new ResponseEntity<>(dto,HttpStatus.OK);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<String> deleteFlight(@PathVariable() int id){
        flightService.deleteFlight(id);
        return new ResponseEntity<>("Deleted Flight with id:"+id, HttpStatus.OK);
    }


    @PatchMapping("{id}")
    public ResponseEntity<FlightDto> updateFlight(@PathVariable() int id,@RequestBody FlightDto dto) {
        FlightDto updated = flightService.updateFlight(id, dto);
        return new ResponseEntity<>(updated, HttpStatus.OK);
    }
}
