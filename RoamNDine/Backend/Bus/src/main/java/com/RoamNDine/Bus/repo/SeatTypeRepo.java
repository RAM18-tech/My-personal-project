package com.RoamNDine.Bus.repo;

import com.RoamNDine.Bus.entity.SeatType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SeatTypeRepo extends JpaRepository<SeatType,Integer> {

    List<SeatType> findByBus_Id(int busId);
}
