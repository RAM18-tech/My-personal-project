package com.RoamNDine.Bus.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SeatTypeDto {

    private int id;

    private String seatType;
    private int totalSeats;
    private int price;
    private int availableSeats;

}
