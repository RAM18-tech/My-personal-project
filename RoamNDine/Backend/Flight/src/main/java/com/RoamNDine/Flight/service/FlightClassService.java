package com.RoamNDine.Flight.service;

import com.RoamNDine.Flight.dto.FlightClassDto;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public interface FlightClassService {

    List<FlightClassDto> getAllClasses();

    List<FlightClassDto> getClassesByFlight(int flightId);

    FlightClassDto getClassById(int id);

    void addClass(int flightId, FlightClassDto dto);

    FlightClassDto updateClass(int id, FlightClassDto dto);

    void deleteClass(int id);

}
