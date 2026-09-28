package com.cts.userService.service;

import com.cts.userService.dto.UserResponseDto;

import java.util.List;

public interface UserService {
    List<UserResponseDto> getAllUsers();
    List<UserResponseDto> getUsersByRole(String roleName);
    UserResponseDto getUserByUsername(String username);
    String getNameByUsername(String username);
    UserResponseDto updateUserName(String username, String newName);
    String deleteUser(String username);
}