package com.RoamNDine.Flight.repo;

import com.RoamNDine.Flight.entity.FlightClass;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FlightClassRepo extends JpaRepository<FlightClass, Integer> {


    List<FlightClass> findByFlight_Id(int flightId);
}

