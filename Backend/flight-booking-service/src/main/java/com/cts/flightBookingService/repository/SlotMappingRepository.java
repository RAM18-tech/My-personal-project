package com.cts.flightBookingService.repository;

import com.cts.flightBookingService.entity.SlotMapping;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SlotMappingRepository extends JpaRepository<SlotMapping, Integer> {
    SlotMapping findBySlotName(String slotName);
}
