package com.cts.userBookingService.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.LocalDateTime;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(InvalidSearchCriteriaException.class)
    public ResponseEntity<ApiException> handleInvalidSearchCriteria(InvalidSearchCriteriaException ex) {
        ApiException api = new ApiException(ex.getMessage(), HttpStatus.BAD_REQUEST.toString(), LocalDateTime.now());
        return new ResponseEntity<>(api, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(InvalidUserIdException.class)
    public ResponseEntity<ApiException> handleInvalidUserId(InvalidUserIdException ex) {
        ApiException api = new ApiException(ex.getMessage(), HttpStatus.NOT_FOUND.toString(), LocalDateTime.now());
        return new ResponseEntity<>(api, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(InvalidFlightIdException.class)
    public ResponseEntity<ApiException> handleInvalidFlightId(InvalidFlightIdException ex) {
        ApiException api = new ApiException(ex.getMessage(), HttpStatus.NOT_FOUND.toString(), LocalDateTime.now());
        return new ResponseEntity<>(api, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(BookingNotFoundException.class)
    public ResponseEntity<ApiException> handleBookingNotFound(BookingNotFoundException ex) {
        ApiException api = new ApiException(ex.getMessage(), HttpStatus.NOT_FOUND.toString(), LocalDateTime.now());
        return new ResponseEntity<>(api, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiException> handleGenericException(Exception ex) {
        ApiException api = new ApiException("Internal server error", HttpStatus.INTERNAL_SERVER_ERROR.toString(), LocalDateTime.now());
        return new ResponseEntity<>(api, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
