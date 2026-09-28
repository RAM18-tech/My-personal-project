package com.cts.flightBookingService.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class FlightDto {

    private String flightNumber;
    private String departureDate;
    private String departureTime;
    private String arrivalTime;
    private int airlineId;
    private int originId;
    private int destinationId;
    private int classTypeId;
    private int cost;

}
