package com.cts.flightBookingService.exception;


import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.LocalDateTime;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(InvalidFlightIdException.class)
    public ResponseEntity<ApiException> handleInvalidFlightId(InvalidFlightIdException ex) {
        ApiException api = new ApiException(ex.getMessage(), HttpStatus.NOT_FOUND.toString(), LocalDateTime.now());
        return new ResponseEntity<>(api, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(InvalidDateRangeException.class)
    public ResponseEntity<ApiException> handleInvalidDateRange(InvalidDateRangeException ex) {
        ApiException api = new ApiException(ex.getMessage(), HttpStatus.BAD_REQUEST.toString(), LocalDateTime.now());
        return new ResponseEntity<>(api, HttpStatus.BAD_REQUEST);
    }


    @ExceptionHandler(InvalidDatabaseEmptyException.class)
    public ResponseEntity<ApiException> handleInvalidDatabaseEmpty(InvalidDatabaseEmptyException ex) {
        ApiException api = new ApiException(ex.getMessage(), HttpStatus.NOT_FOUND.toString(), LocalDateTime.now());
        return new ResponseEntity<>(api, HttpStatus.NOT_FOUND);
    }


    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiException> handleGenericException(Exception ex) {
        ApiException api = new ApiException("Internal server error", HttpStatus.INTERNAL_SERVER_ERROR.toString(), LocalDateTime.now());
        return new ResponseEntity<>(api, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(SameOriginDestinationException.class)
    public ResponseEntity<ApiException> handleSameOriginDestination(SameOriginDestinationException ex) {
        ApiException api = new ApiException(ex.getMessage(), HttpStatus.BAD_REQUEST.toString(), LocalDateTime.now());
        return new ResponseEntity<>(api, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(InvalidCityIdException.class)
    public ResponseEntity<ApiException> handleInvalidCityId(InvalidCityIdException ex) {
        ApiException api = new ApiException(ex.getMessage(), HttpStatus.NOT_FOUND.toString(), LocalDateTime.now());
        return new ResponseEntity<>(api, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(InvalidAirlineIdException.class)
    public ResponseEntity<ApiException> handleInvalidAirlineId(InvalidAirlineIdException ex) {
        ApiException api = new ApiException(ex.getMessage(), HttpStatus.NOT_FOUND.toString(), LocalDateTime.now());
        return new ResponseEntity<>(api, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(CityLinkedToFlightException.class)
    public ResponseEntity<ApiException> handleCityLinkedToFlight(CityLinkedToFlightException ex) {
        ApiException api = new ApiException(ex.getMessage(), HttpStatus.CONFLICT.toString(), LocalDateTime.now());
        return new ResponseEntity<>(api, HttpStatus.CONFLICT);
    }

    @ExceptionHandler(AirlineLinkedToFlightException.class)
    public ResponseEntity<ApiException> handleAirlineLinkedToFlight(AirlineLinkedToFlightException ex) {
        ApiException api = new ApiException(ex.getMessage(), HttpStatus.CONFLICT.toString(), LocalDateTime.now());
        return new ResponseEntity<>(api, HttpStatus.CONFLICT);
    }


}
