package com.cts.userBookingService.exception;

public class InvalidFlightIdException extends RuntimeException {
    public InvalidFlightIdException(String message) {
        super(message);
    }
}