package com.cts.flightBookingService.repository;

import com.cts.flightBookingService.entity.Flight;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FlightRepository extends JpaRepository<Flight, Integer> {

    boolean existsByAirline_Id(int airlineId);
    boolean existsByOrigin_Id(int cityId);
    boolean existsByDestination_Id(int cityId);

}
