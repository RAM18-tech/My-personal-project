package com.cts.flightBookingService.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class ClassType {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;


    private String type;

    public ClassType(int id) {
        this.id = id;
    }
}
