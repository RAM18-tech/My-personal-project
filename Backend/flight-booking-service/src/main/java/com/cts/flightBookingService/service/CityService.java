package com.cts.flightBookingService.service;

import com.cts.flightBookingService.dto.CityDto;
import java.util.List;

public interface CityService {

    CityDto addCity(CityDto dto);
    CityDto updateCity(int id, CityDto dto);
    void deleteCity(int id);
    List<CityDto> getAllCities();
    CityDto getCityById(int id);
}
