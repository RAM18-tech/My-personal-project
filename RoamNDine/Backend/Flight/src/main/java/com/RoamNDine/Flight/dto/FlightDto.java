package com.RoamNDine.Flight.dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class FlightDto {

    private int id;

    private int airLineNumber;

    private String airLineName;

    private String source;

    private String destination;

    private String departureTime;

    private String arrivalTime;

    private String duration;

    private List<FlightClassDto> classes;
}
