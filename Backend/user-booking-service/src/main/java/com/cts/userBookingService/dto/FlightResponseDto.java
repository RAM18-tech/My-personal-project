package com.cts.userBookingService.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class FlightResponseDto {

    private int id;
    private String flightNumber;
    private String departureDate;
    private String departureTime;
    private String arrivalTime;

    private String airlineName;
    private String originName;
    private String destinationName;
    private String classTypeName;
    private String departureSlotName;
    private String arrivalSlotName;
    private int cost;
}
