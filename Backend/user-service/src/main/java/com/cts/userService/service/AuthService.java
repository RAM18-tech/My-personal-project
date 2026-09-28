package com.cts.userService.service;


import com.cts.userService.dto.LoginDto;
import com.cts.userService.dto.RegisterDto;

public interface AuthService {
    String register(RegisterDto registerDto);
    String login(LoginDto loginDto);
}