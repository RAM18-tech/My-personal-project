package com.RoamNDine.Flight.service;


import com.RoamNDine.Flight.dto.FlightDto;
import com.RoamNDine.Flight.entity.Flight;
import com.RoamNDine.Flight.repo.FlightRepo;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class FlightServiceImpl implements FlightService {

    private final FlightRepo flightRepo;
    private final ModelMapper modelMapper;

    @Override
    public List<FlightDto> getAllFlights() {
        List<Flight> flights = flightRepo.findAll();
        return flights.stream()
                .map(flight -> modelMapper.map(flight, FlightDto.class))
                .collect(Collectors.toList());
    }

    @Override
    public FlightDto getFlightById(int id) {
        Flight flight = flightRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Flight not found with id: " + id));

        return modelMapper.map(flight, FlightDto.class);
    }

    @Override
    public void addFlight(FlightDto dto) {
        Flight flight = modelMapper.map(dto, Flight.class);
        flightRepo.save(flight);
    }

    @Override
    public void deleteFlight(int id) {
        flightRepo.deleteById(id);
    }

    @Override
    public FlightDto updateFlight(int id, FlightDto dto) {

        Flight flight = flightRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Flight not found with id: " + id));

        if (dto.getAirLineNumber() != 0) {
            flight.setAirLineNumber(dto.getAirLineNumber());
        }
        if (dto.getAirLineName() != null) {
            flight.setAirLineName(dto.getAirLineName());
        }
        if (dto.getSource() != null) {
            flight.setSource(dto.getSource());
        }
        if (dto.getDestination() != null) {
            flight.setDestination(dto.getDestination());
        }
        if (dto.getDepartureTime() != null) {
            flight.setDepartureTime(dto.getDepartureTime());
        }
        if (dto.getArrivalTime() != null) {
            flight.setArrivalTime(dto.getArrivalTime());
        }
        if (dto.getDuration() != null) {
            flight.setDuration(dto.getDuration());
        }

        Flight updatedFlight = flightRepo.save(flight);
        return modelMapper.map(updatedFlight, FlightDto.class);
    }
}
