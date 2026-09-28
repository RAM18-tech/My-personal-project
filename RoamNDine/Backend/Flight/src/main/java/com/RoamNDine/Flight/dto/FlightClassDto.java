package com.RoamNDine.Flight.dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class FlightClassDto {


    private int id;
    private String classType;
    private int totalSeats;
    private int price;
    private int availableSeats;

}
