package com.cts.flightBookingService.exception;

public class InvalidFlightIdException extends RuntimeException {
    public InvalidFlightIdException(String message) {
        super(message);
    }
}
