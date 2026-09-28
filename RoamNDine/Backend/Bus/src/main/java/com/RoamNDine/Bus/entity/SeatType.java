package com.RoamNDine.Bus.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class SeatType {

    @Id
    private int id;

    private String seatType;
    private int totalSeats;
    private int price;
    private int availableSeats;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "bus_id")
    private Bus bus;
}
