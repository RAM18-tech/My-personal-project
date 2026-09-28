package com.cts.userBookingService.controller;

import com.cts.userBookingService.dto.FlightResponseDto;
import com.cts.userBookingService.dto.FlightSearchDto;
import com.cts.userBookingService.service.FlightSearchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;

import java.util.List;

@RestController
@RequestMapping("/flights")
public class FlightSearchController {

    @Autowired
    private FlightSearchService flightSearchService;

    @PostMapping("/search")
    public ResponseEntity<List<FlightResponseDto>> searchFlights(@Valid @RequestBody FlightSearchDto searchDto) {
        List<FlightResponseDto> flights = flightSearchService.searchFlights(searchDto);
        return ResponseEntity.ok(flights);
    }


}
