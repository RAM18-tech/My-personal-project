package com.RoamNDine.Bus.controller;


import com.RoamNDine.Bus.dto.BusDto;
import com.RoamNDine.Bus.service.BusSerivce;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/bus")
@RequiredArgsConstructor
public class BusController {

    private final BusSerivce busSerivce;

    @GetMapping("/all")
    public ResponseEntity<List<BusDto>> getAllBuses(){
        List<BusDto> buses=busSerivce.getAllBuses();
        return new ResponseEntity<>(buses, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<BusDto> getBusById(@PathVariable() int id){
        BusDto dto= busSerivce.getById(id);
        return new ResponseEntity<>(dto,HttpStatus.OK);
    }

    @PostMapping("/add")
    public ResponseEntity<String> addBus(@RequestBody BusDto dto){
        busSerivce.addBus(dto);
        return new ResponseEntity<>("Bus data added",HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteBus(@PathVariable int id){
        busSerivce.deleteBus(id);
        return new ResponseEntity<>("deleted bus data", HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<BusDto> updateBus(@PathVariable int id , @RequestBody BusDto dto ){
        BusDto update = busSerivce.updateBus(id,dto);
        return new ResponseEntity<>(update,HttpStatus.OK);
    }

}
