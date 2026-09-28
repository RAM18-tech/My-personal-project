package com.cts.userBookingService.feign;

import com.cts.userBookingService.dto.UserDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "user-service")
public interface AuthUserClient {
    @GetMapping("/user-api/users/{username}")
    UserDto getUserByUsername(@PathVariable("username") String username);
}
