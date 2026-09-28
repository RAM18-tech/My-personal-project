package com.cts.userBookingService.feign;


import com.cts.userBookingService.dto.*;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@FeignClient(name = "admin-flight-service")
public interface AdminFlightClient {

    @GetMapping("/admin/flights")
    List<FlightResponseDto> getAllFlights();

    @GetMapping("/admin/cities/{id}")
    CityDto getCityById(@PathVariable int id);

    @GetMapping("/admin/cities/name/{name}")
    CityDto getCityByName(@PathVariable String name);

    @GetMapping("/admin/airlines/{id}")
    AirlineDto getAirlineById(@PathVariable int id);

    @GetMapping("/admin/class-types/{id}")
    ClassTypeDto getClassTypeById(@PathVariable int id);

    @GetMapping("/admin/slots/{id}")
    SlotMappingDto getSlotById(@PathVariable int id);
}