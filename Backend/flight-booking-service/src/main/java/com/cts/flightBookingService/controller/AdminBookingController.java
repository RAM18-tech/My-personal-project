package com.cts.flightBookingService.controller;

import com.cts.flightBookingService.dto.BookingResponseDto;
import com.cts.flightBookingService.feign.BookingClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/admin/bookings")
public class AdminBookingController {

    @Autowired
    private BookingClient bookingClient;

    @GetMapping
    public ResponseEntity<List<BookingResponseDto>> getAllBookings() {
        List<BookingResponseDto> bookings = bookingClient.getAllBookings();
        return ResponseEntity.ok(bookings);
    }
}