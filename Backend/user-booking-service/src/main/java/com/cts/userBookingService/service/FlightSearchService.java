package com.cts.userBookingService.service;

import com.cts.userBookingService.dto.FlightResponseDto;
import com.cts.userBookingService.dto.FlightSearchDto;

import java.util.List;

public interface FlightSearchService {
    List<FlightResponseDto> searchFlights(FlightSearchDto searchDto);
}
