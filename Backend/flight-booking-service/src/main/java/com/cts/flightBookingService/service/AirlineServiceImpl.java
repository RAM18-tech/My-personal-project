package com.cts.flightBookingService.service;

import com.cts.flightBookingService.dto.AirlineDto;
import com.cts.flightBookingService.entity.Airline;
import com.cts.flightBookingService.exception.AirlineLinkedToFlightException;
import com.cts.flightBookingService.exception.InvalidAirlineIdException;
import com.cts.flightBookingService.repository.AirlineRepository;
import com.cts.flightBookingService.repository.FlightRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class AirlineServiceImpl implements AirlineService {

    @Autowired
    private AirlineRepository airlineRepository;

    @Autowired
    private FlightRepository flightRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public AirlineDto addAirline(AirlineDto dto) {
        Airline airline = modelMapper.map(dto, Airline.class);
        Airline saved = airlineRepository.save(airline);
        return modelMapper.map(saved, AirlineDto.class);
    }

    @Override
    public AirlineDto updateAirline(int id, AirlineDto dto) {
        Airline existing = airlineRepository.findById(id)
                .orElseThrow(() -> new InvalidAirlineIdException("Airline ID not found"));
        existing.setName(dto.getName());
        Airline updated = airlineRepository.save(existing);
        return modelMapper.map(updated, AirlineDto.class);
    }

    @Override
    public void deleteAirline(int id) {
        Airline airline = airlineRepository.findById(id)
                .orElseThrow(() -> new InvalidAirlineIdException("Airline ID not found: " + id));
        boolean isLinked = flightRepository.existsByAirline_Id(id);
        if (isLinked) {
            throw new AirlineLinkedToFlightException("Cannot delete airline. It is linked to one or more flights.");
        }
        airlineRepository.delete(airline);
    }

    @Override
    public List<AirlineDto> getAllAirlines() {
        return airlineRepository.findAll().stream()
                .map(a -> modelMapper.map(a, AirlineDto.class))
                .collect(Collectors.toList());
        }

    @Override
    public AirlineDto getAirlineById(int id) {
        Airline airline = airlineRepository.findById(id)
                .orElseThrow(() -> new InvalidAirlineIdException("Airline not found with id: " + id));
        return modelMapper.map(airline, AirlineDto.class);
    }
}
