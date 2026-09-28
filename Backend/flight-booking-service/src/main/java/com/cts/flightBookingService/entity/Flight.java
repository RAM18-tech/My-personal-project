package com.cts.flightBookingService.entity;


import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Entity
public class Flight {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String flightNumber;
    private String departureDate;
    private String departureTime;
    private String arrivalTime;
    private int cost;

    @ManyToOne
    private Airline airline;

    @ManyToOne
    private City origin;

    @ManyToOne
    private City destination;

    @ManyToOne
    private ClassType classType;

    @ManyToOne
    private SlotMapping departureSlot;

    @ManyToOne
    private SlotMapping arrivalSlot;
}
