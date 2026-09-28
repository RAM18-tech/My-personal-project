package com.cts.flightBookingService.exception;

public class InvalidDatabaseEmptyException extends RuntimeException {
    public InvalidDatabaseEmptyException(String message) {
        super(message);
    }
}
