package com.cts.flightBookingService.repository;

import com.cts.flightBookingService.entity.City;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CityRepository extends JpaRepository<City, Integer> {
}
