package com.cts.userBookingService.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BookingResponseDto {
    private int bookingId;
    private String passengerName;
    private String status;

    private String userName;           // ✅ display name from user service
    private FlightResponseDto flight;
}
