package com.delitech.revealing.auth.service;

import com.delitech.revealing.auth.jwt.JwtService;
import com.delitech.revealing.auth.util.AuthResponse;
import com.delitech.revealing.auth.util.LoginRequest;
import com.delitech.revealing.repository.UserRepository;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

@Service
public record AuthService(UserRepository userRepository, JwtService jwtService, AuthenticationManager authenticationManager) {

    public AuthResponse login(LoginRequest request) {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword()));
        UserDetails user = userRepository.findByEmail(request.getUsername()).orElseThrow();
        String token = jwtService.getToken(user);
        return AuthResponse.builder()
                .token(token)
                .build();
    }

}
