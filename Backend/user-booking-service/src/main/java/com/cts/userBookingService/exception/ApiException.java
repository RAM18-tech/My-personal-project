package com.cts.userBookingService.exception;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
public class ApiException {

    private String message;
    private String status;
    private LocalDateTime timestamp;

    public ApiException(String message) {
        this.message = message;
        this.status = "BAD_REQUEST";
        this.timestamp = LocalDateTime.now();
    }
}
