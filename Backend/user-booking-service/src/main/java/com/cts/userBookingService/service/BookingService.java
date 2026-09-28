package com.cts.userBookingService.service;

import com.cts.userBookingService.dto.BookingDto;
import com.cts.userBookingService.dto.BookingResponseDto;

import java.util.List;

public interface BookingService {
    BookingResponseDto createBooking(BookingDto bookingDto);
    List<BookingResponseDto> getBookingsByUsername(String username); // ✅ updated
    BookingResponseDto updateBooking(int bookingId, BookingDto bookingDto);
    void cancelBooking(int bookingId);
    List<BookingResponseDto> getAllBookings();
    BookingResponseDto getBookingById(int id);
}
