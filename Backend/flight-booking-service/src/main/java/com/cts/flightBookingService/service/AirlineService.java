package com.cts.flightBookingService.service;

import com.cts.flightBookingService.dto.AirlineDto;
import java.util.List;

public interface AirlineService {

    AirlineDto addAirline(AirlineDto dto);
    AirlineDto updateAirline(int id, AirlineDto dto);
    void deleteAirline(int id);
    List<AirlineDto> getAllAirlines();
    AirlineDto getAirlineById(int id);
}
