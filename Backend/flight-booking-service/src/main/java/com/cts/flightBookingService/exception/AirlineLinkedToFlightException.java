package com.cts.flightBookingService.exception;

public class AirlineLinkedToFlightException extends RuntimeException {
    public AirlineLinkedToFlightException(String message) {
        super(message);
    }
}
