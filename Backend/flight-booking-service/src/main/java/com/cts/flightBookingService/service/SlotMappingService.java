package com.cts.flightBookingService.service;

import com.cts.flightBookingService.dto.SlotMappingDto;

import java.util.List;

public interface SlotMappingService {
    SlotMappingDto addSlot(SlotMappingDto dto);
    SlotMappingDto updateSlot(int id, SlotMappingDto dto);
    void deleteSlot(int id);
    List<SlotMappingDto> getAllSlots();
    SlotMappingDto getSlotById(int id);
}
