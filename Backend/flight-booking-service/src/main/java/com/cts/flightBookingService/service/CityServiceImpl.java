package com.cts.flightBookingService.service;

import com.cts.flightBookingService.dto.CityDto;
import com.cts.flightBookingService.entity.City;
import com.cts.flightBookingService.exception.CityLinkedToFlightException;
import com.cts.flightBookingService.exception.InvalidCityIdException;
import com.cts.flightBookingService.repository.CityRepository;
import com.cts.flightBookingService.repository.FlightRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CityServiceImpl implements CityService {

    @Autowired
    private CityRepository cityRepository;

    @Autowired
    private FlightRepository flightRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public CityDto addCity(CityDto dto) {
        City city = modelMapper.map(dto, City.class);
        City saved = cityRepository.save(city);
        return modelMapper.map(saved, CityDto.class);
    }

    @Override
    public CityDto updateCity(int id, CityDto dto) {
        City existing = cityRepository.findById(id)
                .orElseThrow(() -> new InvalidCityIdException("City ID not found"));
        existing.setName(dto.getName());
        City updated = cityRepository.save(existing);
        return modelMapper.map(updated, CityDto.class);
    }

    @Override
    public void deleteCity(int id) {
        City city = cityRepository.findById(id)
                .orElseThrow(() -> new InvalidCityIdException("City ID not found: " + id));
        boolean isLinked = flightRepository.existsByOrigin_Id(id) || flightRepository.existsByDestination_Id(id);
        if (isLinked) {
            throw new CityLinkedToFlightException("Cannot delete city. It is linked to one or more flights.");
        }
        cityRepository.delete(city);
    }

    @Override
    public List<CityDto> getAllCities() {
        return cityRepository.findAll().stream()
                .map(c -> modelMapper.map(c, CityDto.class))
                .collect(Collectors.toList());
    }

    @Override
    public CityDto getCityById(int id) {
        City city = cityRepository.findById(id)
                .orElseThrow(() -> new InvalidCityIdException("City not found with id: " + id));
        return modelMapper.map(city, CityDto.class);
    }
}
