package com.cts.userService.service;

import com.cts.userService.dto.UserResponseDto;
import com.cts.userService.entity.Role;
import com.cts.userService.entity.User;
import com.cts.userService.exception.ApiException;
import com.cts.userService.repository.UserRepository;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final ModelMapper modelMapper;

    public UserServiceImpl(UserRepository userRepository, ModelMapper modelMapper) {
        this.userRepository = userRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public List<UserResponseDto> getAllUsers() {
        List<User> users = userRepository.findAll();
        return users.stream().map(this::mapToDto).collect(Collectors.toList());
    }

    @Override
    public List<UserResponseDto> getUsersByRole(String roleName) {
        List<User> users = userRepository.findAllByRoles_Name(roleName);
        return users.stream().map(this::mapToDto).collect(Collectors.toList());
    }

    @Override
    public UserResponseDto getUserByUsername(String username) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new ApiException("User not found with username: " + username));
        return mapToDto(user);
    }

    @Override
    public String getNameByUsername(String username) {
        // 1. Find the user by their username from the repository
        User user = userRepository.findByUsername(username)
                // 2. If not found, throw an exception
                .orElseThrow(() -> new ApiException("User not found with username: " + username));

        // 3. If found, return the user's name
        return user.getName();
    }

    @Override
    @Transactional // Add this annotation
    public String deleteUser(String username) {
        // 1. Find the user by their username
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new ApiException("User not found with username: " + username));

        // 2. IMPORTANT: Clear the user's roles to remove associations from the join table
        user.getRoles().clear();

        // 3. Now, delete the user. This will no longer violate the constraint.
        userRepository.delete(user);

        // 4. Return a success message
        return "User '" + username + "' deleted successfully.";
    }

    @Override
    public UserResponseDto updateUserName(String username, String newName) {
        // 1. Find the existing user from the database
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new ApiException("User not found with username: " + username));

        // 2. Set the new name directly on the user entity
        user.setName(newName);

        // 3. Save the updated user back to the database
        User updatedUser = userRepository.save(user);

        // 4. Convert the final entity to a response DTO and return it
        return mapToDto(updatedUser);
    }

    // Helper method to convert Entity to DTO
    private UserResponseDto mapToDto(User user) {
        UserResponseDto dto = modelMapper.map(user, UserResponseDto.class);
        // Manually map the roles from Set<Role> to Set<String>
        dto.setRoles(user.getRoles().stream()
                .map(Role::getName)
                .collect(Collectors.toSet()));
        return dto;
    }
}