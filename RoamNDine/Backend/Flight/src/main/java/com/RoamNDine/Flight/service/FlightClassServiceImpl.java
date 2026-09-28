package com.RoamNDine.Flight.service;

import com.RoamNDine.Flight.dto.FlightClassDto;
import com.RoamNDine.Flight.entity.Flight;
import com.RoamNDine.Flight.entity.FlightClass;
import com.RoamNDine.Flight.repo.FlightClassRepo;
import com.RoamNDine.Flight.repo.FlightRepo;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class FlightClassServiceImpl implements FlightClassService{

    private final FlightClassRepo classRepo;
    private final FlightRepo flightRepo;
    private final ModelMapper modelMapper;

    @Override
    public List<FlightClassDto> getAllClasses() {
        return classRepo.findAll()
                .stream()
                .map(cls -> modelMapper.map(cls, FlightClassDto.class))
                .collect(Collectors.toList());
    }

    @Override
    public List<FlightClassDto> getClassesByFlight(int flightId) {
        return classRepo.findByFlight_Id(flightId)
                .stream()
                .map(cls -> modelMapper.map(cls, FlightClassDto.class))
                .collect(Collectors.toList());
    }

    @Override
    public FlightClassDto getClassById(int id) {
        FlightClass cls = classRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Flight class not found")); //Copilot added

        return modelMapper.map(cls, FlightClassDto.class);
    }

    @Override
    public void addClass(int flightId, FlightClassDto dto) {

        Flight flight = flightRepo.findById(flightId)
                .orElseThrow(() -> new RuntimeException("Flight not found")); //Copilot added

        FlightClass cls = modelMapper.map(dto, FlightClass.class);
        cls.setFlight(flight);

        classRepo.save(cls);
    }

    @Override
    public FlightClassDto updateClass(int id, FlightClassDto dto) {

        FlightClass cls = classRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Class not found")); //Copilot added

        if (dto.getClassType() != null) cls.setClassType(dto.getClassType());
        if (dto.getTotalSeats() != 0) cls.setTotalSeats(dto.getTotalSeats());
        if (dto.getPrice() != 0) cls.setPrice(dto.getPrice());
        if (dto.getAvailableSeats() != 0) cls.setAvailableSeats(dto.getAvailableSeats());

        FlightClass updated = classRepo.save(cls);

        return modelMapper.map(updated, FlightClassDto.class);
    }

    @Override
    public void deleteClass(int id) {
        classRepo.deleteById(id);
    }

}
