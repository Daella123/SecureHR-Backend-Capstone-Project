package com.daella.employeemanagement.service;


import com.daella.employeemanagement.dto.AuthenticationRequest;
import com.daella.employeemanagement.dto.AuthenticationResponse;
import com.daella.employeemanagement.dto.RegisterRequest;
import com.daella.employeemanagement.dto.UserResponse;
import com.daella.employeemanagement.model.Role;
import com.daella.employeemanagement.model.User;
import com.daella.employeemanagement.repository.UserRepository;
import io.jsonwebtoken.Claims;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;


@Service
@RequiredArgsConstructor
public class UserService implements UserDetailsService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;


    @Transactional
    public AuthenticationResponse register(RegisterRequest request) {
        // validate uniqueness
        if (userRepository.existsByUsername(request.getUsername())) {
            throw new IllegalArgumentException("Username is already taken");
        }
        if (userRepository.existsByEmail(request.getEmail())) {
            throw new IllegalArgumentException("Email is already registered");
        }

        // build the new user
        User newUser = User.builder()
                .username(request.getUsername())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .role(Role.USER)
                .build();
        User saved = userRepository.save(newUser);

        // generate JWT token for the newly created user
        String token = jwtService.generateToken(saved);
        long expiresAt = jwtService.extractClaim(token, Claims::getExpiration).getTime();
        return AuthenticationResponse.builder()
                .accessToken(token)
                .expiresAt(expiresAt)
                .user(toUserResponse(saved))
                .build();
    }


    @Transactional(readOnly = true)
    public AuthenticationResponse authenticate(AuthenticationRequest request) {
        String identifier = request.getUsernameOrEmail();
        Optional<User> optionalUser = userRepository.findByUsernameOrEmail(identifier, identifier);
        User user = optionalUser.orElseThrow(() -> new UsernameNotFoundException("User not found"));
        if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            throw new BadCredentialsException("Invalid credentials");
        }
        String token = jwtService.generateToken(user);
        long expiresAt = jwtService.extractClaim(token, Claims::getExpiration).getTime();
        return AuthenticationResponse.builder()
                .accessToken(token)
                .expiresAt(expiresAt)
                .user(toUserResponse(user))
                .build();
    }


    private long savedJwtExpiry(String token) {
        return jwtService.extractClaim(token, Claims::getExpiration).getTime();
    }


    private UserResponse toUserResponse(User user) {
        return UserResponse.builder()
                .id(user.getId())
                .username(user.getUsername())
                .email(user.getEmail())
                .role(user.getRole())
                .build();
    }


    @Override
    @Transactional(readOnly = true)
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository
                .findByUsernameOrEmail(username, username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
    }
}