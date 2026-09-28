package com.cts.flightBookingService.service;

import com.cts.flightBookingService.dto.SlotMappingDto;
import com.cts.flightBookingService.entity.SlotMapping;
import com.cts.flightBookingService.repository.SlotMappingRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class SlotMappingServiceImpl implements SlotMappingService {

    @Autowired
    private SlotMappingRepository slotMappingRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public SlotMappingDto addSlot(SlotMappingDto dto) {
        SlotMapping slot = modelMapper.map(dto, SlotMapping.class);
        SlotMapping saved = slotMappingRepository.save(slot);
        return modelMapper.map(saved, SlotMappingDto.class);
    }

    @Override
    public SlotMappingDto updateSlot(int id, SlotMappingDto dto) {
        SlotMapping slot = slotMappingRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Slot not found"));
        slot.setSlotName(dto.getSlotName());
        SlotMapping updated = slotMappingRepository.save(slot);
        return modelMapper.map(updated, SlotMappingDto.class);
    }

    @Override
    public void deleteSlot(int id) {
        slotMappingRepository.deleteById(id);
    }

    @Override
    public List<SlotMappingDto> getAllSlots() {
        return slotMappingRepository.findAll().stream()
                .map(s -> modelMapper.map(s, SlotMappingDto.class))
                .collect(Collectors.toList());
    }

    @Override
    public SlotMappingDto getSlotById(int id) {
        SlotMapping slot = slotMappingRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Slot not found"));
        return modelMapper.map(slot, SlotMappingDto.class);
    }
}
