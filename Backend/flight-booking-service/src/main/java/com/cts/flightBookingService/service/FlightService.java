package com.cts.flightBookingService.service;

import com.cts.flightBookingService.dto.FlightDto;
import com.cts.flightBookingService.dto.FlightResponseDto;
import java.util.List;

public interface FlightService {

    FlightResponseDto addFlight(FlightDto flightDto);
    FlightResponseDto updateFlight(int id, FlightDto flightDto);
    void deleteFlight(int id);
    List<FlightResponseDto> getAllFlights();
    FlightResponseDto getFlightById(int id);
}
