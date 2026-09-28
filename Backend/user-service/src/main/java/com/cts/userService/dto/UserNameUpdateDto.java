package com.cts.userService.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor // Needed for JSON deserialization
public class UserNameUpdateDto {
    private String name;
}