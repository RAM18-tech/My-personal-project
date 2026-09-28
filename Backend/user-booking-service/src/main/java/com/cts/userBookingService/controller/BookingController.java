package com.cts.userBookingService.controller;

import com.cts.userBookingService.dto.BookingDto;
import com.cts.userBookingService.dto.BookingResponseDto;
import com.cts.userBookingService.service.BookingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/bookings")
public class BookingController {

    @Autowired
    private BookingService bookingService;

    @GetMapping("/admin/all")
    public ResponseEntity<List<BookingResponseDto>> getAllBookingsForAdmin() {
        List<BookingResponseDto> bookings = bookingService.getAllBookings();
        return ResponseEntity.ok(bookings);
    }

    @PostMapping
    public ResponseEntity<BookingResponseDto> createBooking(@RequestBody BookingDto bookingDto) {
        BookingResponseDto response = bookingService.createBooking(bookingDto);
        return ResponseEntity.ok(response);
    }

    // ✅ Updated to use username instead of userId
    @GetMapping("/user/{username}")
    public ResponseEntity<List<BookingResponseDto>> getBookingsByUser(@PathVariable String username) {
        List<BookingResponseDto> bookings = bookingService.getBookingsByUsername(username);
        return ResponseEntity.ok(bookings);
    }

    @GetMapping("/{id}")
    public ResponseEntity<BookingResponseDto> getBookingById(@PathVariable int id) {
        BookingResponseDto response = bookingService.getBookingById(id);
        return ResponseEntity.ok(response);
    }

    @PutMapping("/{bookingId}")
    public ResponseEntity<BookingResponseDto> updateBooking(@PathVariable int bookingId,
                                                            @RequestBody BookingDto bookingDto) {
        BookingResponseDto updated = bookingService.updateBooking(bookingId, bookingDto);
        return ResponseEntity.ok(updated);
    }

    @DeleteMapping("/{bookingId}")
    public ResponseEntity<Void> cancelBooking(@PathVariable int bookingId) {
        bookingService.cancelBooking(bookingId);
        return ResponseEntity.noContent().build();
    }
}
