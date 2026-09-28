package com.cts.userBookingService.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BookingDto {
    private String username;           // ✅ replaces userId
    private int flightId;
    private String passengerName;
    private String status;
}
