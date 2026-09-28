package com.cts.flightBookingService.exception;

public class InvalidCityIdException extends RuntimeException {
    public InvalidCityIdException(String message) {
        super(message);
    }
}

