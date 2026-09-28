package com.RoamNDine.Flight.repo;

import com.RoamNDine.Flight.entity.Flight;
import org.springframework.data.jpa.repository.JpaRepository;



public interface FlightRepo extends JpaRepository<Flight, Integer> {
}
