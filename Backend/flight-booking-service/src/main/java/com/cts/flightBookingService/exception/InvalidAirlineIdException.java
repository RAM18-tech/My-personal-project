package com.cts.flightBookingService.exception;

public class InvalidAirlineIdException extends RuntimeException {
    public InvalidAirlineIdException(String message) {
        super(message);
    }
}
