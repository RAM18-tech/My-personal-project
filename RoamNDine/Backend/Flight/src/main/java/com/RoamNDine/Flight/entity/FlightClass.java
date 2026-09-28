package com.RoamNDine.Flight.entity;


import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class FlightClass {



    @Id
    private int id;

    private String classType;
    private int totalSeats;
    private int price;
    private int availableSeats;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "flight_id")
    private Flight flight;

}
