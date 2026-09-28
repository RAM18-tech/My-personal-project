package com.cts.userBookingService.service;

import com.cts.userBookingService.dto.BookingDto;
import com.cts.userBookingService.dto.BookingResponseDto;
import com.cts.userBookingService.dto.FlightResponseDto;
import com.cts.userBookingService.dto.UserDto;
import com.cts.userBookingService.entity.Booking;
import com.cts.userBookingService.exception.BookingNotFoundException;
import com.cts.userBookingService.feign.AdminFlightClient;
import com.cts.userBookingService.feign.AuthUserClient;
import com.cts.userBookingService.repository.BookingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;
@Service
public class BookingServiceImpl implements BookingService {

    @Autowired
    private BookingRepository bookingRepository;

    @Autowired
    private AdminFlightClient adminFlightClient;

    @Autowired
    private AuthUserClient authUserClient;

    @Override
    public BookingResponseDto createBooking(BookingDto bookingDto) {
        Booking booking = new Booking();
        booking.setUsername(bookingDto.getUsername()); // ✅ updated
        booking.setFlightId(bookingDto.getFlightId());
        booking.setPassengerName(bookingDto.getPassengerName());
        booking.setStatus("CONFIRMED");

        Booking saved = bookingRepository.save(booking);
        return mapToResponseDto(saved);
    }

    @Override
    public List<BookingResponseDto> getBookingsByUsername(String username) {
        return bookingRepository.findByUsername(username).stream()
                .map(this::mapToResponseDto)
                .collect(Collectors.toList());
    }

    @Override
    public BookingResponseDto updateBooking(int bookingId, BookingDto bookingDto) {
        Booking booking = bookingRepository.findById(bookingId)
                .orElseThrow(() -> new BookingNotFoundException("Booking ID not found"));

        booking.setPassengerName(bookingDto.getPassengerName());
        booking.setStatus(bookingDto.getStatus());
        booking.setFlightId(bookingDto.getFlightId());
        booking.setUsername(bookingDto.getUsername()); // ✅ updated

        Booking updated = bookingRepository.save(booking);
        return mapToResponseDto(updated);
    }

    @Override
    public void cancelBooking(int bookingId) {
        Booking booking = bookingRepository.findById(bookingId)
                .orElseThrow(() -> new BookingNotFoundException("Booking ID not found"));

        booking.setStatus("CANCELLED");
        bookingRepository.save(booking);
    }

    @Override
    public List<BookingResponseDto> getAllBookings() {
        return bookingRepository.findAll().stream()
                .map(this::mapToResponseDto)
                .collect(Collectors.toList());
    }

    @Override
    public BookingResponseDto getBookingById(int id) {
        Booking booking = bookingRepository.findById(id)
                .orElseThrow(() -> new BookingNotFoundException("Booking not found with ID: " + id));
        return mapToResponseDto(booking);
    }

    private BookingResponseDto mapToResponseDto(Booking booking) {
        BookingResponseDto response = new BookingResponseDto();
        response.setBookingId(booking.getId());
        response.setPassengerName(booking.getPassengerName());
        response.setStatus(booking.getStatus());

        UserDto user = authUserClient.getUserByUsername(booking.getUsername()); // ✅ updated
        response.setUserName(user != null ? user.getName() : "Unknown");

        List<FlightResponseDto> flights = adminFlightClient.getAllFlights();
        FlightResponseDto flight = flights.stream()
                .filter(f -> f.getId() == booking.getFlightId())
                .findFirst()
                .orElse(null);

        response.setFlight(flight);
        return response;
    }
}
