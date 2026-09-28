package com.RoamNDine.Bus.service;

import com.RoamNDine.Bus.dto.BusDto;
import com.RoamNDine.Bus.entity.Bus;
import com.RoamNDine.Bus.repo.BusRepo;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class BusServiceImpl implements BusSerivce{

    private final BusRepo busRepo;

    private final ModelMapper modelMapper;


    @Override
    public List<BusDto> getAllBuses() {
        return busRepo.findAll().stream()
                .map(bus -> modelMapper.map(bus,BusDto.class))
                .collect(Collectors.toList());
    }

    @Override
    public BusDto getById(int id) {

        Bus bus =busRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Id not found "));

        return modelMapper.map(bus,BusDto.class);
    }

    @Override
    public void addBus(BusDto dto) {

        busRepo.save(modelMapper.map(dto,Bus.class));
    }

    @Override
    public void deleteBus(int id) {

        busRepo.deleteById(id);
    }

    @Override
    public BusDto updateBus(int id, BusDto dto) {
        Bus bus = busRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Id not found "));

        if( dto.getBusNumber()!= 0)
            bus.setBusNumber(dto.getBusNumber());

        if( dto.getBusName()!= null)
            bus.setBusName(dto.getBusName());

        if(dto.getSource()!= null)
            bus.setSource(dto.getSource());

        if(dto.getDestination()!= null)
            bus.setDestination(dto.getDestination());

        if(dto.getArrivalTime()!= null)
            bus.setArrivalTime(dto.getArrivalTime());

        if(dto.getDepartureTime()!= null )
            bus.setDepartureTime(dto.getDepartureTime());

        if(dto.getDuration()!= null )
            bus.setDuration(dto.getDuration());

        Bus bus1=busRepo.save(bus);

        return modelMapper.map(bus1,BusDto.class);
    }
}
