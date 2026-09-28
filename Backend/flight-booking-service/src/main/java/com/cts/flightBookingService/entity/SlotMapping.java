package com.cts.flightBookingService.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class SlotMapping {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;


    private String slotName;

    public SlotMapping(int id) {
        this.id = id;
    }
}
