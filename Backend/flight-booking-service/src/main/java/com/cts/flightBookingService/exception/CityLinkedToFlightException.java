package com.cts.flightBookingService.exception;

public class CityLinkedToFlightException extends RuntimeException {
    public CityLinkedToFlightException(String message) {
        super(message);
    }
}
