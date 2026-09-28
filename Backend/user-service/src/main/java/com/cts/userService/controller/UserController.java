package com.cts.userService.controller;

import com.cts.userService.dto.UserNameUpdateDto;
import com.cts.userService.dto.UserResponseDto;
import com.cts.userService.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/user-api/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    /**
     * Get a list of all users OR all users with a specific role.
     * Only accessible by users with the 'ADMIN' role.
     *
     * Example Usage:
     * GET /user-api/users -> returns all users
     * GET /user-api/users?role=agent -> returns all users with ROLE_AGENT
     */
    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<UserResponseDto>> getUsers(@RequestParam(required = false) String role) {
        List<UserResponseDto> users;
        if (role != null && role.equalsIgnoreCase("agent")) {
            users = userService.getUsersByRole("ROLE_AGENT");
        } else {
            users = userService.getAllUsers();
        }
        return ResponseEntity.ok(users);
    }

    /**
     * Get details for a single user by their username.
     * Accessible by an ADMIN, OR by the user themselves.
     */
    @GetMapping("/{username}")
    @PreAuthorize("hasRole('ADMIN') or #username == authentication.name")
    public ResponseEntity<UserResponseDto> getUserByUsername(@PathVariable String username) {
        UserResponseDto userDto = userService.getUserByUsername(username);
        return ResponseEntity.ok(userDto);
    }

    /**
     * Get just the name for a single user by their username.
     * Accessible by an ADMIN, OR by the user themselves.
     */
    @GetMapping("/{username}/name")
    @PreAuthorize("hasRole('ADMIN') or #username == authentication.name")
    public ResponseEntity<Map<String, String>> getUserName(@PathVariable String username) {
        String name = userService.getNameByUsername(username);

        // Returning a Map to provide a clean JSON response: {"name": "John Doe"}
        Map<String, String> response = Collections.singletonMap("name", name);
        return ResponseEntity.ok(response);
    }

    /**
     * Update the name for a single user.
     * Accessible by an ADMIN, OR by the user themselves.
     */
    @PatchMapping("/{username}/name")
    @PreAuthorize("hasRole('ADMIN') or #username == authentication.principal.username")
    public ResponseEntity<UserResponseDto> updateUserName(
            @PathVariable String username,
            @RequestBody UserNameUpdateDto nameUpdateDto) {
        UserResponseDto updatedUser = userService.updateUserName(username, nameUpdateDto.getName());
        return ResponseEntity.ok(updatedUser);
    }

    /**
     * Delete a user by their username.
     * Accessible by an ADMIN, OR by the user themselves.
     */
    @DeleteMapping("/{username}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Map<String, String>> deleteUser(@PathVariable String username) {
        String message = userService.deleteUser(username);

        // Create a response map to provide a clean JSON response: {"message": "User 'john' deleted successfully."}
        Map<String, String> response = Collections.singletonMap("message", message);

        return ResponseEntity.ok(response);
    }


}