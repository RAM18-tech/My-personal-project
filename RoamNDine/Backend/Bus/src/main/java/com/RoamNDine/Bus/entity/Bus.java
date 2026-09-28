package com.RoamNDine.Bus.entity;


import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Bus {

    @Id
    private int id;

    private int busNumber;

    private String busName;

    private String source;

    private String destination;

    private String departureTime;

    private String arrivalTime;

    private String duration;

    @OneToMany(mappedBy = "bus", cascade = CascadeType.ALL)
    private List<SeatType> types;

}
