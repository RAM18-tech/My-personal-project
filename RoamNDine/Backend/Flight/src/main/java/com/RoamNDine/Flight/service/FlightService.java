package com.RoamNDine.Flight.service;

import com.RoamNDine.Flight.dto.FlightDto;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface FlightService {

    List<FlightDto> getAllFlights();

    FlightDto getFlightById(int id);

    void addFlight(FlightDto dto);

    void deleteFlight(int id);

    FlightDto updateFlight(int id, FlightDto dto);

}
