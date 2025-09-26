package com.example.practice1antonovayulia.service;

import com.example.practice1antonovayulia.model.Role;
import com.example.practice1antonovayulia.model.User;
import com.example.practice1antonovayulia.repository.UserRepository;
import com.example.practice1antonovayulia.config.JwtUtil;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.Optional;

@Service
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final JwtUtil jwtUtil;

    public AuthService(UserRepository userRepository,
                       PasswordEncoder passwordEncoder,
                       AuthenticationManager authenticationManager,
                       JwtUtil jwtUtil) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.authenticationManager = authenticationManager;
        this.jwtUtil = jwtUtil;
    }

    public Optional<String> register(String username, String password, String role) {
        if (userRepository.findByUsername(username).isPresent()) {
            return Optional.empty();
        }
        User user = new User();
        user.setUsername(username);
        user.setPassword(passwordEncoder.encode(password));
        // Set roles as a singleton set with the Role enum parsed from role string
        user.setRoles(Collections.singleton(Role.valueOf(role.toUpperCase())));
        userRepository.save(user);
        return Optional.of("User registered successfully");
    }

    public Optional<String> login(String username, String password) {
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
            User user = userRepository.findByUsername(username).orElseThrow();
            String jwt = jwtUtil.generateToken(user.getUsername());
            return Optional.of(jwt);
        } catch (AuthenticationException e) {
            return Optional.empty();
        }
    }
}
