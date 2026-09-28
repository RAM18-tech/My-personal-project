package com.cts.userBookingService.service;

import com.cts.userBookingService.dto.FlightResponseDto;
import com.cts.userBookingService.dto.FlightSearchDto;
import com.cts.userBookingService.exception.InvalidSearchCriteriaException;
import com.cts.userBookingService.feign.AdminFlightClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class FlightSearchServiceImpl implements FlightSearchService {

    @Autowired
    private AdminFlightClient adminFlightClient;

    @Override
    public List<FlightResponseDto> searchFlights(FlightSearchDto searchDto) {
        if (searchDto.getOriginName() == null || searchDto.getOriginName().trim().isEmpty()) {
            throw new InvalidSearchCriteriaException("Invalid originName");
        }
        if (searchDto.getDestinationName() == null || searchDto.getDestinationName().trim().isEmpty()) {
            throw new InvalidSearchCriteriaException("Invalid destinationName");
        }
        if (searchDto.getOriginName().equalsIgnoreCase(searchDto.getDestinationName())) {
            throw new InvalidSearchCriteriaException("Origin and destination cannot be the same");
        }
        if (searchDto.getDepartureDate() != null && searchDto.getDepartureDate().isBefore(LocalDate.now())) {
            throw new InvalidSearchCriteriaException("Departure date cannot be in the past");
        }

        List<FlightResponseDto> allFlights = adminFlightClient.getAllFlights();

        return allFlights.stream()
                .filter(flight -> flight.getOriginName().equalsIgnoreCase(searchDto.getOriginName()))
                .filter(flight -> flight.getDestinationName().equalsIgnoreCase(searchDto.getDestinationName()))
                .filter(flight -> flight.getDepartureDate().equals(searchDto.getDepartureDate()))
                .filter(flight -> searchDto.getAirlines() == null || searchDto.getAirlines().isEmpty()
                        || searchDto.getAirlines().contains(flight.getAirlineName()))
                .filter(flight -> searchDto.getMinPrice() == null || flight.getCost() >= searchDto.getMinPrice())
                .filter(flight -> searchDto.getMaxPrice() == null || flight.getCost() <= searchDto.getMaxPrice())
                .filter(flight -> searchDto.getDepartureSlot() == null || flight.getDepartureSlotName().equalsIgnoreCase(searchDto.getDepartureSlot()))
                .filter(flight -> searchDto.getArrivalSlot() == null || flight.getArrivalSlotName().equalsIgnoreCase(searchDto.getArrivalSlot()))
                .collect(Collectors.toList());
    }
}
