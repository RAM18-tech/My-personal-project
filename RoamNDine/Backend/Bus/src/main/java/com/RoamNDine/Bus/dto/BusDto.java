package com.RoamNDine.Bus.dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BusDto {

    private int id;

    private int busNumber;

    private String busName;

    private String source;

    private String destination;

    private String departureTime;

    private String arrivalTime;

    private String duration;

    private List<SeatTypeDto> types;

}
