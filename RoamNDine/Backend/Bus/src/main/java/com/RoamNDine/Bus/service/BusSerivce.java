package com.RoamNDine.Bus.service;


import com.RoamNDine.Bus.dto.BusDto;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface BusSerivce {

    List<BusDto> getAllBuses();

    BusDto getById(int id);

    void addBus(BusDto dto);

    void deleteBus(int id);

    BusDto updateBus(int id , BusDto dto);




}
