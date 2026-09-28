package com.cts.flightBookingService.controller;

import com.cts.flightBookingService.dto.SlotMappingDto;
import com.cts.flightBookingService.service.SlotMappingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/admin/slots")
public class SlotMappingController {

    @Autowired
    private SlotMappingService slotMappingService;

    @PostMapping
    public ResponseEntity<SlotMappingDto> addSlot(@RequestBody SlotMappingDto dto) {
        return new ResponseEntity<>(slotMappingService.addSlot(dto), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<SlotMappingDto> updateSlot(@PathVariable int id, @RequestBody SlotMappingDto dto) {
        return new ResponseEntity<>(slotMappingService.updateSlot(id, dto), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteSlot(@PathVariable int id) {
        slotMappingService.deleteSlot(id);
        return ResponseEntity.ok("Slot deleted with ID: " + id);
    }

    @GetMapping
    public ResponseEntity<List<SlotMappingDto>> getAllSlots() {
        return ResponseEntity.ok(slotMappingService.getAllSlots());
    }

    @GetMapping("/{id}")
    public ResponseEntity<SlotMappingDto> getSlotById(@PathVariable int id) {
        return ResponseEntity.ok(slotMappingService.getSlotById(id));
    }
}
