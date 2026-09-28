package com.cts.flightBookingService.controller;

import com.cts.flightBookingService.dto.CityDto;
import com.cts.flightBookingService.service.CityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/admin/cities")
public class CityController {

    @Autowired
    private CityService cityService;

    @PostMapping
    public ResponseEntity<CityDto> addCity(@RequestBody CityDto dto) {
        return new ResponseEntity<>(cityService.addCity(dto), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<CityDto> updateCity(@PathVariable int id, @RequestBody CityDto dto) {
        return new ResponseEntity<>(cityService.updateCity(id, dto), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteCity(@PathVariable int id) {
        cityService.deleteCity(id);
        return ResponseEntity.ok("City deleted with ID: " + id);
    }

    @GetMapping
    public ResponseEntity<List<CityDto>> getAllCities() {
        return new ResponseEntity<>(cityService.getAllCities(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<CityDto> getCityById(@PathVariable int id) {
        return ResponseEntity.ok(cityService.getCityById(id));
    }
}
