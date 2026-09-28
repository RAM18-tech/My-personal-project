package com.RoamNDine.Bus.service;

import com.RoamNDine.Bus.dto.SeatTypeDto;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface SeatTypeService {

    List<SeatTypeDto> getAllSeatTypes();

    SeatTypeDto getSeatTypeByID(int id);

    void addSeatType(SeatTypeDto dto);

    void deleteSeatType(int id);

    SeatTypeDto updateSeatType(int id, SeatTypeDto dto);

    List<SeatTypeDto> getSeatTypeByBus(int busId);

}
