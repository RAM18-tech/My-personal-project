package com.cts.userBookingService.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import jakarta.validation.constraints.NotBlank;
import java.time.LocalDate;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class FlightSearchDto {
    @NotBlank
    private String originName;
    
    @NotBlank
    private String destinationName;
    private LocalDate departureDate;

    private Boolean nonStop;
    private Boolean oneStop;

    private List<String> airlines;

    private Integer minPrice;
    private Integer maxPrice;

    private String departureSlot;
    private String arrivalSlot;
}
