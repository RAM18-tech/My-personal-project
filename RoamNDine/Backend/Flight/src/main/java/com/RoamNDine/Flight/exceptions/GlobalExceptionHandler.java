package com.RoamNDine.Flight.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;

@ControllerAdvice
@RestController
public class GlobalExceptionHandler {
    //Copilot added
    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<String> handleRuntimeException(RuntimeException ex) {
        String message = ex.getMessage();
        if (message != null && message.contains("Flight not found")) {
            return new ResponseEntity<>(message, HttpStatus.NOT_FOUND); //Copilot added
        } else if (message != null && message.contains("Flight class not found")) {
            return new ResponseEntity<>(message, HttpStatus.NOT_FOUND); //Copilot added
        } else if (message != null && message.contains("Class not found")) {
            return new ResponseEntity<>(message, HttpStatus.NOT_FOUND); //Copilot added
        }
        return new ResponseEntity<>(message, HttpStatus.BAD_REQUEST); //Copilot added
    }
}
