package com.chatapp.service;

import com.chatapp.repository.UserRepository;
import com.chatapp.security.JwtService;
import com.chatapp.dto.LoginRequest;
import com.chatapp.dto.LoginResponse;
import com.chatapp.dto.RegisterRequest;
import com.chatapp.dto.UserResponse;
import com.chatapp.entity.User;

import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;

    public AuthService(
            UserRepository userRepository,
            PasswordEncoder passwordEncoder,
            JwtService jwtService) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtService = jwtService;
    }

    public User register(RegisterRequest request) {
        User user = User.builder()
                .name(request.name())
                .email(request.email())
                .password(passwordEncoder.encode(request.password()))
                .build();

        return userRepository.save(user);
    }

    public LoginResponse login(LoginRequest request) {

        User user = userRepository.findByEmail(request.email())
                .orElseThrow(() -> new BadCredentialsException("Invalid email or password"));

        if (!passwordEncoder.matches(
                request.password(),
                user.getPassword())) {
            throw new BadCredentialsException("Invalid email or password");
        }

        String token = jwtService.generateToken(
                user.getId(),
                user.getEmail());

        return new LoginResponse(
                token,
                UserResponse.from(user));
    }

}
