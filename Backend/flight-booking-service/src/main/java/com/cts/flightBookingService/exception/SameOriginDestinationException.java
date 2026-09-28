package com.cts.flightBookingService.exception;

public class SameOriginDestinationException extends RuntimeException {
    public SameOriginDestinationException(String message) {
        super(message);
    }
}
