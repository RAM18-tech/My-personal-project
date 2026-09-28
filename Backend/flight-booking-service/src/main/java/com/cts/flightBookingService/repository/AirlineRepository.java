package com.cts.flightBookingService.repository;

import com.cts.flightBookingService.entity.Airline;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AirlineRepository extends JpaRepository<Airline, Integer> {
}
