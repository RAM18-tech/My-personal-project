package com.cts.flightBookingService.feign;

import com.cts.flightBookingService.dto.BookingResponseDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@FeignClient(name = "user-booking-service")
public interface BookingClient {
    @GetMapping("/bookings/admin/all")
    List<BookingResponseDto> getAllBookings();
}
