package com.cts.flightBookingService.service;

import com.cts.flightBookingService.dto.FlightDto;
import com.cts.flightBookingService.dto.FlightResponseDto;
import com.cts.flightBookingService.entity.*;
import com.cts.flightBookingService.exception.InvalidDatabaseEmptyException;
import com.cts.flightBookingService.exception.InvalidDateRangeException;
import com.cts.flightBookingService.exception.InvalidFlightIdException;
import com.cts.flightBookingService.exception.SameOriginDestinationException;
import com.cts.flightBookingService.repository.FlightRepository;
import com.cts.flightBookingService.repository.SlotMappingRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class FlightServiceImpl implements FlightService {

    @Autowired
    private FlightRepository flightRepository;

    @Autowired
    private SlotMappingRepository slotMappingRepository;

    @Autowired
    private ModelMapper modelMapper;

    private SlotMapping resolveSlot(String time) {
        int hour = Integer.parseInt(time.split(":")[0]);
        String slotName;

        if (hour < 6) slotName = "Before 6 AM";
        else if (hour >= 6 && hour < 12) slotName = "6 AM – 12 PM";
        else if (hour >= 12 && hour < 18) slotName = "12 PM – 6 PM";
        else slotName = "After 6 PM";

        return slotMappingRepository.findBySlotName(slotName);
    }


    @Override
    public FlightResponseDto addFlight(FlightDto dto) {
        if (dto.getOriginId() == dto.getDestinationId()) {
            throw new SameOriginDestinationException("Origin and destination cannot be the same");
        }
        if (LocalDate.parse(dto.getDepartureDate()).isBefore(LocalDate.now())) {
            throw new InvalidDateRangeException("Departure date cannot be in the past");
        }


        Flight flight = new Flight();
        flight.setFlightNumber(dto.getFlightNumber());
        flight.setDepartureDate(dto.getDepartureDate());
        flight.setDepartureTime(dto.getDepartureTime());
        flight.setArrivalTime(dto.getArrivalTime());
        flight.setCost(dto.getCost());

        flight.setAirline(new Airline(dto.getAirlineId()));
        flight.setOrigin(new City(dto.getOriginId()));
        flight.setDestination(new City(dto.getDestinationId()));
        flight.setClassType(new ClassType(dto.getClassTypeId()));

        flight.setDepartureSlot(resolveSlot(dto.getDepartureTime()));
        flight.setArrivalSlot(resolveSlot(dto.getArrivalTime()));

        Flight saved = flightRepository.save(flight);
        return mapToResponseDto(saved);
    }

    @Override
    public FlightResponseDto updateFlight(int id, FlightDto dto) {
        if (dto.getOriginId() == dto.getDestinationId()) {
            throw new SameOriginDestinationException("Origin and destination cannot be the same");
        }
        if (LocalDate.parse(dto.getDepartureDate()).isBefore(LocalDate.now())) {
            throw new InvalidDateRangeException("Departure date cannot be in the past");
        }


        Flight flight = flightRepository.findById(id)
                .orElseThrow(() -> new InvalidFlightIdException("Flight ID not found"));

        flight.setFlightNumber(dto.getFlightNumber());
        flight.setDepartureDate(dto.getDepartureDate());
        flight.setDepartureTime(dto.getDepartureTime());
        flight.setArrivalTime(dto.getArrivalTime());
        flight.setCost(dto.getCost());

        flight.setAirline(new Airline(dto.getAirlineId()));
        flight.setOrigin(new City(dto.getOriginId()));
        flight.setDestination(new City(dto.getDestinationId()));
        flight.setClassType(new ClassType(dto.getClassTypeId()));

        flight.setDepartureSlot(resolveSlot(dto.getDepartureTime()));
        flight.setArrivalSlot(resolveSlot(dto.getArrivalTime()));

        Flight updated = flightRepository.save(flight);
        return mapToResponseDto(updated);
    }

    @Override
    public void deleteFlight(int id) {
        Flight flight = flightRepository.findById(id)
                .orElseThrow(() -> new InvalidFlightIdException("Flight ID not found"));
        flightRepository.delete(flight);
    }

    @Override
    public List<FlightResponseDto> getAllFlights() {
        List<Flight> flights = flightRepository.findAll();
        if (flights.isEmpty()) {
            throw new InvalidDatabaseEmptyException("No flights available in the database");
        }
        return flights.stream()
                .map(this::mapToResponseDto)
                .collect(Collectors.toList());
    }

    @Override
    public FlightResponseDto getFlightById(int id) {
        Flight flight = flightRepository.findById(id)
                .orElseThrow(() -> new InvalidFlightIdException("Flight not found with id: " + id));
        return mapToResponseDto(flight);
    }

    private FlightResponseDto mapToResponseDto(Flight flight) {
        FlightResponseDto dto = new FlightResponseDto();
        dto.setId(flight.getId());
        dto.setFlightNumber(flight.getFlightNumber());
        dto.setDepartureDate(flight.getDepartureDate());
        dto.setDepartureTime(flight.getDepartureTime());
        dto.setArrivalTime(flight.getArrivalTime());
        dto.setCost(flight.getCost());
        dto.setAirlineName(flight.getAirline().getName());
        dto.setOriginName(flight.getOrigin().getName());
        dto.setDestinationName(flight.getDestination().getName());
        dto.setClassTypeName(flight.getClassType().getType());
        dto.setDepartureSlotName(flight.getDepartureSlot().getSlotName());
        dto.setArrivalSlotName(flight.getArrivalSlot().getSlotName());
        return dto;
    }
}
