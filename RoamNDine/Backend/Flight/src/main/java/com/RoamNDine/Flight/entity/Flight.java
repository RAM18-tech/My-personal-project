package com.RoamNDine.Flight.entity;


import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;


@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Flight {

    @Id
    private int id;

    private int airLineNumber;

    private String airLineName;

    private String source;

    private String destination;

    private String departureTime;

    private String arrivalTime;

    private String duration;


    @OneToMany(mappedBy = "flight", cascade = CascadeType.ALL)
    private List<FlightClass> classes;



}
