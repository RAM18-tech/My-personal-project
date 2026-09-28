package com.cts.userService.service;


import com.cts.userService.auth.JwtTokenProvider;
import com.cts.userService.dto.LoginDto;
import com.cts.userService.dto.RegisterDto;
import com.cts.userService.entity.Role;
import com.cts.userService.entity.User;
import com.cts.userService.exception.ApiException;
import com.cts.userService.repository.RoleRepository;
import com.cts.userService.repository.UserRepository;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Service
public class AuthServiceImpl implements AuthService {

    private final AuthenticationManager authenticationManager;
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenProvider jwtTokenProvider;

    public AuthServiceImpl(AuthenticationManager authenticationManager,
                           UserRepository userRepository,
                           RoleRepository roleRepository,
                           PasswordEncoder passwordEncoder,
                           JwtTokenProvider jwtTokenProvider) {
        this.authenticationManager = authenticationManager;
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtTokenProvider = jwtTokenProvider;
    }

    @Override
    public String login(LoginDto loginDto) {
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                loginDto.getUsernameOrEmail(),
                loginDto.getPassword()
        ));

        SecurityContextHolder.getContext().setAuthentication(authentication);
        return jwtTokenProvider.generateToken(authentication);
    }

    @Override
    public String register(RegisterDto registerDto) {
        // Check if username is already taken
        if (userRepository.existsByUsername(registerDto.getUsername())) {
            throw new ApiException("Username is already taken!");
        }

        // Check if email is already taken
        if (userRepository.existsByEmail(registerDto.getEmail())) {
            throw new ApiException("Email is already taken!");
        }

        User user = new User();
        user.setName(registerDto.getName());
        user.setUsername(registerDto.getUsername());
        user.setEmail(registerDto.getEmail());
        user.setPassword(passwordEncoder.encode(registerDto.getPassword()));

        Set<Role> roles = new HashSet<>();

        // ✨ --- MODIFIED LOGIC --- ✨
        // 1. Assign ROLE_USER to every new user by default.
        Role userRole = roleRepository.findByName("ROLE_USER")
                .orElseThrow(() -> new ApiException("Error: Role 'ROLE_USER' is not found."));
        roles.add(userRole);

        // 2. If the user checked the "Register as Agent" box, add ROLE_AGENT as well.
        if (registerDto.isRegisterAsAgent()) {
            Role agentRole = roleRepository.findByName("ROLE_AGENT")
                    .orElseThrow(() -> new ApiException("Error: Role 'ROLE_AGENT' is not found."));
            roles.add(agentRole);
        }
        // ✨ --- END OF MODIFICATION --- ✨

        user.setRoles(roles);
        userRepository.save(user);

        return "User registered successfully!";
    }
}
